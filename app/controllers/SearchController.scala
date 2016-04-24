package controllers

import javax.inject.{Singleton, Inject}

import scala.concurrent.duration._
import scala.concurrent.Future

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout

import play.api.http.ContentTypes
import play.api.libs.json.Json
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.{WSClient}
import play.api.mvc.Results.Ok
import play.api.mvc.{Controller, Action}

import common.Client
import services.SearchService
import services.SearchService.{Search, SearchResults, msgResultsWrites}


@Singleton
class SearchController @Inject()(system: ActorSystem, ws: WSClient) extends Controller with Client {

  implicit val timeout = Timeout(30 seconds)

  val searchService = system.actorOf(SearchService.props(ws), "search-service")

  def search(q: String, page: Int) = Action.async {
    (searchService ? Search(nextId(), q, page)).mapTo[SearchResults].map { searchResults =>
      Ok(Json.toJson(searchResults.results)).as(ContentTypes.JSON)
    }
  }

}
