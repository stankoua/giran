package models


case class User(login: String, id: Long, avatarUrl: String, contributions: Int)

object User {
  /* TODO defines reads and writes later*/
  import play.api.libs.json.{Reads, Writes, __}
  import play.api.libs.functional.syntax._

  implicit val userReads: Reads[User] = (
    (__ \ "login").read[String] and
    (__ \ "id").read[Long] and
    (__ \ "avatar_url").read[String] and
    (__ \ "contributions").read[Int]
  ) (User.apply _)

  implicit val userWrites: Writes[User] = (
    (__ \ "login").write[String] and
    (__ \ "id").write[Long] and
    (__ \ "avatarUrl").write[String] and
    (__ \ "contributions").write[Int]
  ) (unlift(User.unapply))
}
