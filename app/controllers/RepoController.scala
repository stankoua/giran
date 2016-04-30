package controllers

import javax.inject.{Singleton, Inject}
import scala.concurrent.duration._
import play.api.mvc.{Controller, Action}
import play.api.mvc.Results.{Ok, BadRequest}
import play.api.http.ContentTypes
import play.api.libs.json.Json
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.WSClient
import play.api.mvc.AcceptExtractors //.Accepts
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import common.Client
import models.Repo
import services.RepoService
import services.RepoService.{GetRepo, RepoResult, repoResultWrites, GetRepoCommitters, RepoCommittersResults}
import models.User.userWrites
import scala.concurrent.Future

@Singleton
class RepoController @Inject()(system: ActorSystem, ws: WSClient) extends Controller with Client {

  implicit val timeout = Timeout(120 seconds)

  val repoService = system.actorOf(RepoService.props(ws), "repo-service")

  def getRepo(owner: String = "", name: String = "") = Action.async { implicit request =>
    request match {
      case Accepts.Html() => {
        Future {Ok(views.html.index("Github Repo ANalyser"))}
      }
      case Accepts.Json() if(!owner.isEmpty && !name.isEmpty) => {
          (repoService ? GetRepo(nextId(), owner, name)).mapTo[RepoResult].map { repo =>
            Ok(Json.toJson(repo.result)).as(ContentTypes.JSON)
          }
      }
      case _ => {
        Future{BadRequest("owner and name must be non empty")}
      }
    }
  }

  def getCommitters(owner: String = "", name: String = "") = Action.async {
    (repoService ? GetRepoCommitters(nextId(), owner, name)).mapTo[RepoCommittersResults].map { results =>
      Ok(Json.toJson(results.committers)).as(ContentTypes.JSON)
    }
  }

  def getLastCommits(owner: String, name: String, max: Int) = Action.async {
    (repoService ? GetLastCommits(nextId(), owner, name, count)).mapTo[].map {}
  }

}
