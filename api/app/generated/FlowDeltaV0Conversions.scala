/**
 * Generated by apidoc - http://www.apidoc.me
 * Service version: 0.0.3
 * apidoc:0.11.8 http://www.apidoc.me/flow/delta/0.0.3/anorm_2_x_parsers
 */
package io.flow.delta.v0.anorm.conversions {

  import anorm.{Column, MetaDataItem, TypeDoesNotMatch}
  import play.api.libs.json.{JsArray, JsObject, JsValue}
  import scala.util.{Failure, Success, Try}

  /**
    * Conversions to collections of objects using JSON.
    */
  object Json {

    def parser[T](
      f: play.api.libs.json.JsValue => T
    ) = anorm.Column.nonNull1 { (value, meta) =>
      val MetaDataItem(qualified, nullable, clazz) = meta
      value match {
        case json: org.postgresql.util.PGobject => {
          Try {
            f(
              play.api.libs.json.Json.parse(
                json.getValue
              )
            )
          } match {
            case Success(result) => Right(result)
            case Failure(ex) => Left(
              TypeDoesNotMatch(
                s"Column[$qualified] error parsing json $value: $ex"
              )
            )
          }
        }
        case _=> {
          Left(
            TypeDoesNotMatch(
              s"Column[$qualified] error converting $value: ${value.asInstanceOf[AnyRef].getClass} to Json"
            )
          )
        }


      }
    }

    implicit val columnToJsObject: Column[play.api.libs.json.JsObject] = parser { _.as[play.api.libs.json.JsObject] }

    implicit val columnToSeqBoolean: Column[Seq[Boolean]] = parser { _.as[Seq[Boolean]] }
    implicit val columnToMapBoolean: Column[Map[String, Boolean]] = parser { _.as[Map[String, Boolean]] }
    implicit val columnToSeqDouble: Column[Seq[Double]] = parser { _.as[Seq[Double]] }
    implicit val columnToMapDouble: Column[Map[String, Double]] = parser { _.as[Map[String, Double]] }
    implicit val columnToSeqInt: Column[Seq[Int]] = parser { _.as[Seq[Int]] }
    implicit val columnToMapInt: Column[Map[String, Int]] = parser { _.as[Map[String, Int]] }
    implicit val columnToSeqLong: Column[Seq[Long]] = parser { _.as[Seq[Long]] }
    implicit val columnToMapLong: Column[Map[String, Long]] = parser { _.as[Map[String, Long]] }
    implicit val columnToSeqLocalDate: Column[Seq[_root_.org.joda.time.LocalDate]] = parser { _.as[Seq[_root_.org.joda.time.LocalDate]] }
    implicit val columnToMapLocalDate: Column[Map[String, _root_.org.joda.time.LocalDate]] = parser { _.as[Map[String, _root_.org.joda.time.LocalDate]] }
    implicit val columnToSeqDateTime: Column[Seq[_root_.org.joda.time.DateTime]] = parser { _.as[Seq[_root_.org.joda.time.DateTime]] }
    implicit val columnToMapDateTime: Column[Map[String, _root_.org.joda.time.DateTime]] = parser { _.as[Map[String, _root_.org.joda.time.DateTime]] }
    implicit val columnToSeqBigDecimal: Column[Seq[BigDecimal]] = parser { _.as[Seq[BigDecimal]] }
    implicit val columnToMapBigDecimal: Column[Map[String, BigDecimal]] = parser { _.as[Map[String, BigDecimal]] }
    implicit val columnToSeqJsObject: Column[Seq[_root_.play.api.libs.json.JsObject]] = parser { _.as[Seq[_root_.play.api.libs.json.JsObject]] }
    implicit val columnToMapJsObject: Column[Map[String, _root_.play.api.libs.json.JsObject]] = parser { _.as[Map[String, _root_.play.api.libs.json.JsObject]] }
    implicit val columnToSeqString: Column[Seq[String]] = parser { _.as[Seq[String]] }
    implicit val columnToMapString: Column[Map[String, String]] = parser { _.as[Map[String, String]] }
    implicit val columnToSeqUUID: Column[Seq[_root_.java.util.UUID]] = parser { _.as[Seq[_root_.java.util.UUID]] }
    implicit val columnToMapUUID: Column[Map[String, _root_.java.util.UUID]] = parser { _.as[Map[String, _root_.java.util.UUID]] }

    import io.flow.delta.v0.models.json._
    implicit val columnToSeqDeltaPublication: Column[Seq[_root_.io.flow.delta.v0.models.Publication]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Publication]] }
    implicit val columnToMapDeltaPublication: Column[Map[String, _root_.io.flow.delta.v0.models.Publication]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Publication]] }
    implicit val columnToSeqDeltaRole: Column[Seq[_root_.io.flow.delta.v0.models.Role]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Role]] }
    implicit val columnToMapDeltaRole: Column[Map[String, _root_.io.flow.delta.v0.models.Role]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Role]] }
    implicit val columnToSeqDeltaScms: Column[Seq[_root_.io.flow.delta.v0.models.Scms]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Scms]] }
    implicit val columnToMapDeltaScms: Column[Map[String, _root_.io.flow.delta.v0.models.Scms]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Scms]] }
    implicit val columnToSeqDeltaVisibility: Column[Seq[_root_.io.flow.delta.v0.models.Visibility]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Visibility]] }
    implicit val columnToMapDeltaVisibility: Column[Map[String, _root_.io.flow.delta.v0.models.Visibility]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Visibility]] }
    implicit val columnToSeqDeltaGithubAuthenticationForm: Column[Seq[_root_.io.flow.delta.v0.models.GithubAuthenticationForm]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.GithubAuthenticationForm]] }
    implicit val columnToMapDeltaGithubAuthenticationForm: Column[Map[String, _root_.io.flow.delta.v0.models.GithubAuthenticationForm]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.GithubAuthenticationForm]] }
    implicit val columnToSeqDeltaGithubUser: Column[Seq[_root_.io.flow.delta.v0.models.GithubUser]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.GithubUser]] }
    implicit val columnToMapDeltaGithubUser: Column[Map[String, _root_.io.flow.delta.v0.models.GithubUser]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.GithubUser]] }
    implicit val columnToSeqDeltaGithubUserForm: Column[Seq[_root_.io.flow.delta.v0.models.GithubUserForm]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.GithubUserForm]] }
    implicit val columnToMapDeltaGithubUserForm: Column[Map[String, _root_.io.flow.delta.v0.models.GithubUserForm]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.GithubUserForm]] }
    implicit val columnToSeqDeltaGithubWebhook: Column[Seq[_root_.io.flow.delta.v0.models.GithubWebhook]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.GithubWebhook]] }
    implicit val columnToMapDeltaGithubWebhook: Column[Map[String, _root_.io.flow.delta.v0.models.GithubWebhook]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.GithubWebhook]] }
    implicit val columnToSeqDeltaImage: Column[Seq[_root_.io.flow.delta.v0.models.Image]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Image]] }
    implicit val columnToMapDeltaImage: Column[Map[String, _root_.io.flow.delta.v0.models.Image]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Image]] }
    implicit val columnToSeqDeltaImageForm: Column[Seq[_root_.io.flow.delta.v0.models.ImageForm]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.ImageForm]] }
    implicit val columnToMapDeltaImageForm: Column[Map[String, _root_.io.flow.delta.v0.models.ImageForm]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.ImageForm]] }
    implicit val columnToSeqDeltaItem: Column[Seq[_root_.io.flow.delta.v0.models.Item]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Item]] }
    implicit val columnToMapDeltaItem: Column[Map[String, _root_.io.flow.delta.v0.models.Item]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Item]] }
    implicit val columnToSeqDeltaMembership: Column[Seq[_root_.io.flow.delta.v0.models.Membership]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Membership]] }
    implicit val columnToMapDeltaMembership: Column[Map[String, _root_.io.flow.delta.v0.models.Membership]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Membership]] }
    implicit val columnToSeqDeltaMembershipForm: Column[Seq[_root_.io.flow.delta.v0.models.MembershipForm]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.MembershipForm]] }
    implicit val columnToMapDeltaMembershipForm: Column[Map[String, _root_.io.flow.delta.v0.models.MembershipForm]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.MembershipForm]] }
    implicit val columnToSeqDeltaOrganization: Column[Seq[_root_.io.flow.delta.v0.models.Organization]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Organization]] }
    implicit val columnToMapDeltaOrganization: Column[Map[String, _root_.io.flow.delta.v0.models.Organization]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Organization]] }
    implicit val columnToSeqDeltaOrganizationForm: Column[Seq[_root_.io.flow.delta.v0.models.OrganizationForm]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.OrganizationForm]] }
    implicit val columnToMapDeltaOrganizationForm: Column[Map[String, _root_.io.flow.delta.v0.models.OrganizationForm]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.OrganizationForm]] }
    implicit val columnToSeqDeltaOrganizationSummary: Column[Seq[_root_.io.flow.delta.v0.models.OrganizationSummary]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.OrganizationSummary]] }
    implicit val columnToMapDeltaOrganizationSummary: Column[Map[String, _root_.io.flow.delta.v0.models.OrganizationSummary]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.OrganizationSummary]] }
    implicit val columnToSeqDeltaProject: Column[Seq[_root_.io.flow.delta.v0.models.Project]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Project]] }
    implicit val columnToMapDeltaProject: Column[Map[String, _root_.io.flow.delta.v0.models.Project]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Project]] }
    implicit val columnToSeqDeltaProjectForm: Column[Seq[_root_.io.flow.delta.v0.models.ProjectForm]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.ProjectForm]] }
    implicit val columnToMapDeltaProjectForm: Column[Map[String, _root_.io.flow.delta.v0.models.ProjectForm]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.ProjectForm]] }
    implicit val columnToSeqDeltaProjectState: Column[Seq[_root_.io.flow.delta.v0.models.ProjectState]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.ProjectState]] }
    implicit val columnToMapDeltaProjectState: Column[Map[String, _root_.io.flow.delta.v0.models.ProjectState]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.ProjectState]] }
    implicit val columnToSeqDeltaProjectSummary: Column[Seq[_root_.io.flow.delta.v0.models.ProjectSummary]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.ProjectSummary]] }
    implicit val columnToMapDeltaProjectSummary: Column[Map[String, _root_.io.flow.delta.v0.models.ProjectSummary]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.ProjectSummary]] }
    implicit val columnToSeqDeltaReference: Column[Seq[_root_.io.flow.delta.v0.models.Reference]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Reference]] }
    implicit val columnToMapDeltaReference: Column[Map[String, _root_.io.flow.delta.v0.models.Reference]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Reference]] }
    implicit val columnToSeqDeltaRepository: Column[Seq[_root_.io.flow.delta.v0.models.Repository]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Repository]] }
    implicit val columnToMapDeltaRepository: Column[Map[String, _root_.io.flow.delta.v0.models.Repository]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Repository]] }
    implicit val columnToSeqDeltaState: Column[Seq[_root_.io.flow.delta.v0.models.State]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.State]] }
    implicit val columnToMapDeltaState: Column[Map[String, _root_.io.flow.delta.v0.models.State]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.State]] }
    implicit val columnToSeqDeltaStateForm: Column[Seq[_root_.io.flow.delta.v0.models.StateForm]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.StateForm]] }
    implicit val columnToMapDeltaStateForm: Column[Map[String, _root_.io.flow.delta.v0.models.StateForm]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.StateForm]] }
    implicit val columnToSeqDeltaSubscription: Column[Seq[_root_.io.flow.delta.v0.models.Subscription]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Subscription]] }
    implicit val columnToMapDeltaSubscription: Column[Map[String, _root_.io.flow.delta.v0.models.Subscription]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Subscription]] }
    implicit val columnToSeqDeltaSubscriptionForm: Column[Seq[_root_.io.flow.delta.v0.models.SubscriptionForm]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.SubscriptionForm]] }
    implicit val columnToMapDeltaSubscriptionForm: Column[Map[String, _root_.io.flow.delta.v0.models.SubscriptionForm]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.SubscriptionForm]] }
    implicit val columnToSeqDeltaToken: Column[Seq[_root_.io.flow.delta.v0.models.Token]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Token]] }
    implicit val columnToMapDeltaToken: Column[Map[String, _root_.io.flow.delta.v0.models.Token]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Token]] }
    implicit val columnToSeqDeltaTokenForm: Column[Seq[_root_.io.flow.delta.v0.models.TokenForm]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.TokenForm]] }
    implicit val columnToMapDeltaTokenForm: Column[Map[String, _root_.io.flow.delta.v0.models.TokenForm]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.TokenForm]] }
    implicit val columnToSeqDeltaUserForm: Column[Seq[_root_.io.flow.delta.v0.models.UserForm]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.UserForm]] }
    implicit val columnToMapDeltaUserForm: Column[Map[String, _root_.io.flow.delta.v0.models.UserForm]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.UserForm]] }
    implicit val columnToSeqDeltaUserIdentifier: Column[Seq[_root_.io.flow.delta.v0.models.UserIdentifier]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.UserIdentifier]] }
    implicit val columnToMapDeltaUserIdentifier: Column[Map[String, _root_.io.flow.delta.v0.models.UserIdentifier]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.UserIdentifier]] }
    implicit val columnToSeqDeltaUserSummary: Column[Seq[_root_.io.flow.delta.v0.models.UserSummary]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.UserSummary]] }
    implicit val columnToMapDeltaUserSummary: Column[Map[String, _root_.io.flow.delta.v0.models.UserSummary]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.UserSummary]] }
    implicit val columnToSeqDeltaUsernamePassword: Column[Seq[_root_.io.flow.delta.v0.models.UsernamePassword]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.UsernamePassword]] }
    implicit val columnToMapDeltaUsernamePassword: Column[Map[String, _root_.io.flow.delta.v0.models.UsernamePassword]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.UsernamePassword]] }
    implicit val columnToSeqDeltaVersion: Column[Seq[_root_.io.flow.delta.v0.models.Version]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.Version]] }
    implicit val columnToMapDeltaVersion: Column[Map[String, _root_.io.flow.delta.v0.models.Version]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.Version]] }
    implicit val columnToSeqDeltaItemSummary: Column[Seq[_root_.io.flow.delta.v0.models.ItemSummary]] = parser { _.as[Seq[_root_.io.flow.delta.v0.models.ItemSummary]] }
    implicit val columnToMapDeltaItemSummary: Column[Map[String, _root_.io.flow.delta.v0.models.ItemSummary]] = parser { _.as[Map[String, _root_.io.flow.delta.v0.models.ItemSummary]] }

  }

}