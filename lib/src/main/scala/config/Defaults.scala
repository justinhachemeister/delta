package io.flow.delta.lib.config

import io.flow.delta.lib.BuildNames
import io.flow.delta.config.v0.models

object Defaults {

  val Branch = models.Branch(
    name = "master"
  )

  val Build = models.Build(
    name = BuildNames.DefaultBuildName,
    dockerfile = "./Dockerfile",
    initialNumberInstances = 2,
    instanceType = models.InstanceType.fromString("t2.micro").getOrElse {
      sys.error("Default instance type[t2.micro] not found")
    },
    stages = models.BuildStage.all,
    dependencies = Nil
  )

  val Config = models.ConfigProject(
    stages = models.ProjectStage.all,
    branches = Seq(Branch),
    builds = Seq(Build)
  )

}