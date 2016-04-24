package models

case class Repo(id: Long, name: String, fullName: String, isPrivate: Boolean, url: String, contributorsUrl: String)

object Repo {

  import play.api.libs.json.{Reads, Writes, JsPath, Json, JsString, JsBoolean, JsNumber, JsValue, JsSuccess, JsError, __}
  // import play.api.libs.json._
  import play.api.libs.functional.syntax._

  implicit val modelRepoWrites: Writes[Repo] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "name").write[String] and
    (JsPath \ "fullName").write[String] and
    (JsPath \ "isPrivate").write[Boolean] and
    (JsPath \ "url").write[String] and
    (JsPath \ "contributorsUrl").write[String]
  ) (unlift(Repo.unapply))

  implicit val modelRepoReads: Reads[Repo] = (
    (__ \ "id").read[Long] and
    (__ \ "name").read[String] and
    (__ \ "full_name").read[String] and
    (__ \ "private").read[Boolean] and
    (__ \ "url").read[String] and
    (__ \ "contributors_url").read[String]
  )(Repo.apply _ )

  // implicit object modelRepoReads__Venere extends  Reads[Repo] {
  //   override def reads(o:JsValue): Repo = {
  //     (o \ "id", o \ "name", o \ "full_name", o \ "private", o \ "url", o \ "contributors_url") match {
  //       case (JsNumber(id), JsString(name), JsString(fullName), JsBoolean(isPrivate), JsString(url), JsString(contributorsUrl)) =>
  //         JsSuccess(Repo(id.toLong, name, fullName, isPrivate, url, contributorsUrl))
  //       case _ => JsError("Mal formed Json")
  //     }
  //   }
  // }

}
