package services

import play.api.libs.ws.WSClient

import akka.actor.{Actor, ActorRef, Props, Terminated, ActorLogging}
import akka.event.LoggingReceive

import models.{SearchResults => Results}
import common.Client


object SearchService {

  import play.api.libs.json.{JsPath, Writes}
  import play.api.libs.functional.syntax._
  import models.SearchResults._

  case class Search(id: Long, repo: String)

  case class SearchResults(id: Long, results: Results)

  def props(ws: WSClient) : Props = Props(classOf[SearchService], ws)

  implicit val msgResultsWrites: Writes[SearchResults] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "results").write[Results]
  ) (unlift(SearchResults.unapply))

}

class SearchService(ws: WSClient) extends Actor with Client with ActorLogging {

  import akka.actor.SupervisorStrategy.Stop
  import akka.actor.OneForOneStrategy

  import getters.SearchGetter
  import services.SearchService.{Search, SearchResults}
  import getters.SearchGetter.{StartSearch, SearchDone}

  var pendingRequests : Map[Long, ActorRef] = Map.empty

  override def supervisorStrategy = OneForOneStrategy(0) {
    case _ => Stop
  }

  def receive = LoggingReceive {
    case Search(id, repo) => {
      pendingRequests += (id -> sender())
      val getter = context.actorOf(SearchGetter.props(ws), "search-getter-actor-" + nextId())
      context.watch(getter)
      getter ! StartSearch(id, repo)
    }
    case SearchDone(id, repo, results) => {
      println("========================================>>>>>>>>>>> SearchService")
      log.debug(" =======================> debugging SearchService, id: {}, repo: {}", id, repo)
      val requester = pendingRequests(id)
      requester ! SearchResults(id, results)
      pendingRequests -= id
      context.stop(sender())
    }
    case Terminated(childActor) => {
      println(" =======================> child terminated")
      log.debug(" =======================> child terminated")
      () // throw timeout exception when childs fail
    }
    case _ => ()
  }

}
