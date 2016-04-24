package getters

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import play.api.libs.ws.{WSClient, WSRequest}
import akka.actor.{Props, Actor, ActorLogging}
import akka.event.LoggingReceive
import akka.pattern.pipe

// import net.caoticode.buhtig.Buhtig
// import net.caoticode.buhtig.Converters._
// import org.json4s._
// import org.json4s.native.JsonMethods._

import models.{SearchResults, Repo}


object SearchGetter {

  case class StartSearch(id: Long, repo: String)

  case class SearchDone(id: Long, repo: String, results: SearchResults)

  def props(ws: WSClient) : Props = Props(classOf[SearchGetter], ws)

}

class SearchGetter(ws: WSClient) extends Actor with ActorLogging {

  import getters.SearchGetter.{StartSearch, SearchDone}
  import models.SearchResults.modelSearchResultsReads
  import play.api.libs.json.Json

  // implicit val defaultFormat = DefaultFormats
  implicit val defaultContext = play.api.libs.concurrent.Execution.Implicits.defaultContext

  // val token = "test-token"
  // val buhtig = new Buhtig(token)
  // val client = buhtig.asyncClient

  // val url = "https://api.github.com"
  val url = "https://api.github.com/search/repositories"
  // val url = "http://beta.sidemash.com"

  def receive = active

  def active: Receive = LoggingReceive {
    case StartSearch(id, repo) => {
      println("========================================>>>>>>>>>>> SearchGetter")
      log.debug(" =======================> debugging SearchGetter, id: {}, repo: {}", id, repo)
      // val response = (client.search.repositories ? ("q" -> repo))
      //                   .get[JSON]
      //                   .map { response =>
      //                     println(s"========================================> $response")
      //                     SearchDone(id, repo, response.extract[SearchResults])
      //                   }
      //                   .pipeTo(self)
      ws.url(url)
        .withQueryString("q" -> repo)
        .withHeaders("Accept" -> "application/json")
        .withRequestTimeout(10 seconds)
        .get()
        .map { response =>
          println(s"========================================> ${Json.prettyPrint(response.json)}")
          SearchDone(id, repo, response.json.as[SearchResults])
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
