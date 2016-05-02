package models

import java.time.LocalDateTime
import models.User.{userReads, userWrites}


case class Commit(sha: String, date: LocalDateTime, committer: User)

object Commit {

  import play.api.libs.json.{Reads, Writes, __}
  import play.api.libs.functional.syntax._

  implicit val commitReads: Reads[Commit] = (
    (__ \ "sha").read[String] and
    (__ \ "commit" \ "author" \ "date").read[LocalDateTime] and
    (__ \ "author").read[User]
  )(Commit.apply _)

  implicit val commitWrites: Writes[Commit] = (
    (__ \ "sha").write[String] and
    (__ \ "date").write[LocalDateTime] and
    (__ \ "committer").write[User]
  )(unlift(Commit.unapply))

}
