package models

case class Repo(id: Long,
                name: String,
                fullName: String,
                isPrivate: Boolean,
                url: String,
                contributorsUrl: String,
                // language: String,
                forks: Int,
                openIssues: Int,
                watchers: Int)

object Repo {

  import play.api.libs.json.{Reads, Writes, JsPath, __}
  // import play.api.libs.json._
  import play.api.libs.functional.syntax._

  implicit val modelRepoWrites: Writes[Repo] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "name").write[String] and
    (JsPath \ "fullName").write[String] and
    (JsPath \ "isPrivate").write[Boolean] and
    (JsPath \ "url").write[String] and
    (JsPath \ "contributorsUrl").write[String] and
    // (JsPath \ "language").write[String] and
    (JsPath \ "forks").write[Int] and
    (JsPath \ "openIssues").write[Int] and
    (JsPath \ "watchers").write[Int]
  ) (unlift(Repo.unapply))

  implicit val modelRepoReads: Reads[Repo] = (
    (__ \ "id").read[Long] and
    (__ \ "name").read[String] and
    (__ \ "full_name").read[String] and
    (__ \ "private").read[Boolean] and
    (__ \ "url").read[String] and
    (__ \ "contributors_url").read[String] and
    // (__ \ "language").read[String] and
    (__ \ "forks").read[Int] and
    (__ \ "open_issues").read[Int] and
    (__ \ "watchers").read[Int]
  )(Repo.apply _ )

}
