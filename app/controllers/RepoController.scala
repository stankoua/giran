package controllers

import javax.inject.{Singleton, Inject}
import scala.concurrent.duration._
import play.api.mvc.{Controller, Action}
import play.api.mvc.Results.Ok
import play.api.http.ContentTypes
import play.api.libs.json.Json
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.WSClient
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import common.Client
import models.Repo
import services.RepoService

@Singleton
class RepoController @Inject()(system: ActorSystem, ws: WSClient) extends Controller with Client {

  import services.RepoService.{GetRepo, RepoResult, repoResultWrites}

  implicit val timeout = Timeout(30 seconds)

  val repoService = system.actorOf(RepoService.props(ws), "repo-service")

  def getRepo(owner: String = "", name: String = "") = Action.async {
    // if (owner.isEmpty || name.isEmpty) {
      (repoService ? GetRepo(nextId(), owner, name)).mapTo[RepoResult].map { repo =>
        Ok(Json.toJson(repo)).as(ContentTypes.JSON)
        // Ok(Json.toJson("jlkbl")).as(ContentTypes.JSON)
      }
    // } else
  }

}
