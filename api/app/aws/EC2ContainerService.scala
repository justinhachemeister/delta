package io.flow.delta.aws

import io.flow.delta.api.lib.RegistryClient
import io.flow.delta.v0.models.Version

import com.amazonaws.services.ecs.AmazonECSClient
import com.amazonaws.services.ecs.model._

import collection.JavaConverters._

import play.api.libs.concurrent.Akka
import play.api.Play.current

import scala.concurrent.Future

object EC2ContainerService extends Settings with Credentials {

  private[this] implicit val executionContext = Akka.system.dispatchers.lookup("ec2-context")

  private[this] lazy val client = new AmazonECSClient(awsCredentials)

  /**
  * Name creation helper functions
  **/
  def getClusterName(projectId: String): String = {
    return s"$projectId-cluster"
  }

  def getBaseName(imageName: String, imageVersion: String): String = {
    return Seq(
      s"${imageName.replaceAll("[/]","-")}", // flow/registry becomes flow-registry
      s"${imageVersion.replaceAll("[.]","-")}" // 1.2.3 becomes 1-2-3
    ).mkString("-")
  }

  def getContainerName(imageName: String, imageVersion: String): String = {
    return s"${getBaseName(imageName, imageVersion)}-container"
  }

  def getTaskName(imageName: String, imageVersion: String): String = {
    return s"${getBaseName(imageName, imageVersion)}-task"
  }

  def getServiceName(imageName: String, imageVersion: String): String = {
    return s"${getBaseName(imageName, imageVersion)}-service"
  }

  /**
  * Functions that interact with AWS ECS
  **/
  def createCluster(projectId: String): String = {
    val name = getClusterName(projectId)
    client.createCluster(new CreateClusterRequest().withClusterName(name))
    return name
  }

  // scale to the desired count - can be up or down
  def scale(imageName: String, imageVersion: String, projectId: String, desiredCount: Long): Future[Unit] = {
    val cluster = getClusterName(projectId)
    val service = getServiceName(imageName, imageVersion)

    Future {
      val resp = client.describeServices(
        new DescribeServicesRequest()
        .withCluster(cluster)
        .withServices(Seq(service).asJava)
      )

      // if service doesn't exist in the cluster
      if (resp.getFailures().size() > 0) {
        registerTaskDefinition(imageName, imageVersion, projectId).map { taskDefinition =>
          createService(imageName, imageVersion, projectId, taskDefinition)
        }
      }

      client.updateService(
        new UpdateServiceRequest()
        .withCluster(cluster)
        .withService(service)
        .withDesiredCount(desiredCount.toInt)
      )
    }
  }

  def getClusterInfo(projectId: String): Seq[Version] = {
    val cluster = getClusterName(projectId)

    client.listServices(
      new ListServicesRequest()
      .withCluster(cluster)
    ).getServiceArns().asScala.map{serviceArn =>
      val service = client.describeServices(
        new DescribeServicesRequest()
        .withCluster(cluster)
        .withServices(Seq(serviceArn).asJava)
      ).getServices().asScala.headOption.getOrElse {
        sys.error(s"Service ARN $serviceArn does not exist for cluster $cluster")
      }

      client.describeTaskDefinition(
        new DescribeTaskDefinitionRequest()
        .withTaskDefinition(service.getTaskDefinition)
      ).getTaskDefinition().getContainerDefinitions().asScala.headOption match {
        case None => sys.error(s"No container definitions for task definition ${service.getTaskDefinition}")
        case Some(containerDef) => {
          val image = containerDef.getImage()

          // image name = "flow/user:0.0.1" - o = flow, p = user, version = 0.0.1
          val pattern = "(\\w+)/(\\w+):(.+)".r
          val pattern(o, p, version) = image
          Version(version, service.getRunningCount.toInt)
        }
      }
    }
  }

  def getServiceInfo(imageName: String, imageVersion: String, projectId: String): Option[Service] = {
    val cluster = getClusterName(projectId)
    val service = getServiceName(imageName, imageVersion)

    // should only be one thing, since we are passing cluster and service
    client.describeServices(
      new DescribeServicesRequest()
      .withCluster(cluster)
      .withServices(Seq(service).asJava)
    ).getServices().asScala.headOption
  }

  def registerTaskDefinition(imageName: String, imageVersion: String, projectId: String): Future[String] = {
    val taskName = getTaskName(imageName, imageVersion)
    val containerName = getContainerName(imageName, imageVersion)
    RegistryClient.getById(projectId).map {
      case None => sys.error(s"project[$projectId] was not found in the registry")
      case Some(application) => {
        val registryPorts = application.ports.headOption.getOrElse {
          sys.error(s"project[$projectId] does not have any ports in the registry")
        }

        client.registerTaskDefinition(
          new RegisterTaskDefinitionRequest()
          .withFamily(taskName)
          .withContainerDefinitions(
            Seq(
              new ContainerDefinition()
              .withName(containerName)
              .withImage(imageName + ":" + imageVersion)
              .withMemory(containerMemory)
              .withPortMappings(
                Seq(
                  new PortMapping()
                  .withContainerPort(registryPorts.internal.toInt)
                  .withHostPort(registryPorts.external.toInt)
                ).asJava
              )
              .withCommand(Seq("production").asJava)
            ).asJava
          )
        )

        taskName
      }
    }
  }

  def createService(imageName: String, imageVersion: String, projectId: String, taskDefinition: String): Future[String] = {
    val clusterName = getClusterName(projectId)
    val serviceName = getServiceName(imageName, imageVersion)
    val containerName = getContainerName(imageName, imageVersion)
    val loadBalancerName = ElasticLoadBalancer.getLoadBalancerName(projectId)

    RegistryClient.getById(projectId).map {
      case None => sys.error(s"project[$projectId] was not found in the registry")
      case Some(application) => {
        val registryPorts = application.ports.headOption.getOrElse {
          sys.error(s"project[$projectId] does not have any ports in the registry")
        }

        val internalPort: Int = registryPorts.internal.toInt

        client.createService(
          new CreateServiceRequest()
          .withServiceName(serviceName)
          .withCluster(clusterName)
          .withDesiredCount(createServiceDesiredCount)
          .withRole(serviceRole)
          .withTaskDefinition(taskDefinition)
          .withLoadBalancers(
            Seq(
              new LoadBalancer()
              .withContainerName(containerName)
              .withLoadBalancerName(loadBalancerName)
              .withContainerPort(internalPort)
            ).asJava
          )
        ).getService().getServiceName()
      }
    }
  }

}
