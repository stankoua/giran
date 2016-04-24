package getters

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import play.api.libs.ws.{WSClient, WSRequest}
import akka.actor.{Props, Actor, ActorLogging}
import akka.event.LoggingReceive
import akka.pattern.pipe


import models.{SearchResults, Repo}


object SearchGetter {

  case class StartSearch(id: Long, repo: String, page: Int)

  case class SearchDone(id: Long, repo: String, results: SearchResults)

  def props(ws: WSClient) : Props = Props(classOf[SearchGetter], ws)

}

class SearchGetter(ws: WSClient) extends Actor with ActorLogging {

  import getters.SearchGetter.{StartSearch, SearchDone}
  import models.SearchResults.modelSearchResultsReads
  import play.api.libs.json.{Json, JsNumber, JsValue, JsObject}

  implicit val defaultContext = play.api.libs.concurrent.Execution.Implicits.defaultContext

  val url = "https://api.github.com/search/repositories"

  def receive = active

  def active: Receive = LoggingReceive {
    case StartSearch(id, repo, page) => {
      println("========================================>>>>>>>>>>> SearchGetter")
      log.debug(" =======================> debugging SearchGetter, id: {}, repo: {}", id, repo)
      ws.url(url)
        .withQueryString("q" -> repo)
        .withQueryString("page" -> page.toString)
        .withHeaders("Accept" -> "application/json")
        .withRequestTimeout(10 seconds)
        .get()
        .map { response =>
          // println(s"========================================> ${Json.prettyPrint(response.json)}")
          val result = response.json.as[JsObject] + ("page" -> JsNumber(BigDecimal(page)))
          SearchDone(id, repo, result.as[SearchResults])
        }
        .recover { case e => e.printStackTrace() }
        .pipeTo(self)
      context.become(waiting)
    }
    case _ => {
      println(s"========================================> ACTIVE - undefined: ")
    }
  }

  def waiting: Receive = LoggingReceive {
    case msg@SearchDone(id, repo, results) => {
      println("========================================>>>>>>>>>>> SearchGetter-searchdone")
      log.debug(" =======================> debugging SearchGetter-searchdone, id: {}, repo: {}", id, repo)
      context.parent ! msg
      context.become(active)
    }
    case _ => {
      println(s"========================================> WAITING - undefined: ")
    }
  }

}
