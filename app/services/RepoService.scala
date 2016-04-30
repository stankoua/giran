package services

import play.api.libs.ws.WSClient
import akka.actor.{Actor, Props, ActorRef, Terminated, ActorLogging}
import akka.event.LoggingReceive
import models.{Repo, User}
import common.Client


object RepoService {

  import play.api.libs.json.{__, Writes}
  import play.api.libs.functional.syntax._

  case class GetRepo(id: Long, owner: String, name: String)

  case class RepoResult(id: Long, result: Repo)

  case class GetRepoCommitters(id: Long, owner: String, name: String)

  case class RepoCommittersResults(id: Long, committers: List[User])

  def props(ws: WSClient) = Props(classOf[RepoService], ws)

  implicit val repoResultWrites: Writes[RepoResult] = (
    (__ \ "id").write[Long] and
    (__ \ "result").write[Repo]
  )(unlift(RepoResult.unapply))

}


class RepoService(ws: WSClient) extends Actor with Client with ActorLogging {

  import getters.{RepoGetter, RepoCommittersGetter}
  import services.RepoService.{GetRepo, RepoResult, GetRepoCommitters, RepoCommittersResults}
  import getters.RepoGetter.{FetchRepo, RepoFetchResults}
  import getters.RepoCommittersGetter.{FetchCommitters, CommittersFetched}

  var pendingRequests: Map[Long, ActorRef] = Map.empty

  def receive = LoggingReceive {
    case GetRepo(id, owner, name) => {
      val getter = context.actorOf(RepoGetter.props(ws), "repo-getter-actor-" + nextId())
      context.watch(getter)
      getter ! FetchRepo(id, owner, name)
      pendingRequests += (id -> sender())
    }
    case RepoFetchResults(id, result) => {
      val requester = pendingRequests(id)
      requester ! RepoResult(id, result)
      pendingRequests -= id
      context.stop(sender())
    }
    case GetRepoCommitters(id, owner, name) => {
      val getter = context.actorOf(RepoCommittersGetter.props(ws), "repo-committers-getter-actor" + nextId())
      context.watch(getter)
      getter ! FetchCommitters(id, owner, name)
      pendingRequests += (id -> sender())
    }
    case CommittersFetched(id, committers) => {
      val requester = pendingRequests(id)
      requester ! RepoCommittersResults(id, committers)
      pendingRequests -= id
      context.stop(sender())
    }
    case Terminated(child) => {}
    case _ => ()
  }

}
