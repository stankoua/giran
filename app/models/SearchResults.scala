package models

case class SearchResults(total: Int, page: Int, items: List[Repo])

object SearchResults {

  import org.json4s.{DefaultFormats}
  import play.api.libs.json.{Reads, Writes, JsPath, Json, __}
  import play.api.libs.functional.syntax._
  import models.Repo.{modelRepoReads, modelRepoWrites}

  implicit val modelSearchResultsWrites: Writes[SearchResults] = (
    (JsPath \ "total").write[Int] and
    (JsPath \ "page").write[Int] and
    (JsPath \ "items").write[List[Repo]]
  ) (unlift(SearchResults.unapply))

  implicit val modelSearchResultsReads: Reads[SearchResults] = (
    (__ \ "total_count").read[Int] and
    (__ \ "page").read[Int] and
    (__ \ "items").read[List[Repo]]
  ) (SearchResults.apply _)

}
