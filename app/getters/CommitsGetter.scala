package getters

import akka.actor.{Actor, Props}
import play.api.libs.ws.WSClient
import scala.concurrent.duration._
import akka.pattern.pipe
import scala.collection.immutable.Range
import models.Commit
// import play.api.libs.ws.WSAuthScheme.BASIC

object CommitsGetter {

  case class FetchCommitsRequest(id: Long, owner: String, name: String, page: Int)
  case class FetchCommitsResponse(id: Long, commits: List[Commit])

  def props(ws: WSClient) = Props(classOf[CommitsGetter], ws)

}

class CommitsGetter(ws: WSClient) extends Actor {

  import models.Commit.{commitReads}
  import getters.CommitsGetter.{FetchCommitsRequest, FetchCommitsResponse}

  implicit val defaultContext = play.api.libs.concurrent.Execution.Implicits.defaultContext

  /* FIXME fix url */
  def url(owner: String, name: String, page: Int) = s"https://api.github.com/repos/${owner}/${name}/commits?page=${page}"

  def receive: Receive = active

  def active: Receive = {
    case FetchCommitsRequest(id, owner, name, page) => {
      ws.url(url(owner, name, page))
        .withMethod("get")
        .withHeaders("Accept" -> "application/json")
        .withRequestTimeout(10 seconds)
        .get()
        .map {response =>
          FetchCommitsResponse(id, response.json.as[List[Commit]])
        }
        .recover {case e => e.printStackTrace()}
        .pipeTo(self)
      context.become(waiting)
    }
    case _ => ()
  }

  def waiting: Receive = {
    case msg@FetchCommitsResponse(id, commits) => {
      context.parent ! msg
    }
    case _ => ()
  }

}
