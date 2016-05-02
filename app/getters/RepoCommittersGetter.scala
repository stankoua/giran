package getters

import akka.actor.{Actor, Props}
import akka.pattern.pipe
import play.api.libs.ws.WSClient
import scala.concurrent.duration._
// import play.api.libs.ws.WSAuthScheme.BASIC

import models.User

object RepoCommittersGetter {

  case class FetchCommitters(id: Long, owner: String, name: String)

  case class CommittersFetched(id: Long, committers: List[User])

  def props(ws: WSClient) = Props(classOf[RepoCommittersGetter], ws)

}

class RepoCommittersGetter(ws: WSClient) extends Actor {

  import play.api.libs.json.Json
  import models.User.userReads
  import getters.RepoCommittersGetter.{FetchCommitters, CommittersFetched}

  implicit val defaultContext = play.api.libs.concurrent.Execution.Implicits.defaultContext

  def url(owner: String, name: String) = s"https://api.github.com/repos/$owner/$name/contributors"

  def receive = active

  def active: Receive = {
    case FetchCommitters(id, owner, name) => {
      ws.url(url(owner, name))
        .withMethod("get")
        .withHeaders("Accept" -> "application/json")
        .withRequestTimeout(10 seconds)
        .get()
        .map { response =>
          CommittersFetched(id, response.json.as[List[User]])
        }
        .recover { case e => e.printStackTrace() }
        .pipeTo(self)
      context.become(waiting)
    }
    case _ => ()
  }

  def waiting: Receive = {
    case msg@CommittersFetched(id, committers) => {
      context.parent ! msg
      context.become(active)
    }
    case _ => ()
  }

}
