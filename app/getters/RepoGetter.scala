package getters

import play.api.libs.ws.WSClient
import akka.actor.{Actor, Props, ActorLogging}
import akka.event.LoggingReceive
import akka.pattern.pipe
import models.Repo
import scala.concurrent.duration._


object RepoGetter {

  case class FetchRepo(id: Long, owner: String, name: String)

  case class RepoFetchResults(id: Long, result: Repo)

  def props(ws: WSClient) = Props(classOf[RepoGetter], ws)

}

class RepoGetter(ws: WSClient) extends Actor with ActorLogging {

  import getters.RepoGetter.{FetchRepo, RepoFetchResults}

  implicit val defaultContext = play.api.libs.concurrent.Execution.Implicits.defaultContext

  def url(owner: String, name: String) = s"/repos/$owner/$name"

  def receive = active

  def active: Receive = LoggingReceive {
    case FetchRepo(id, owner, name) => {
      ws.url(url(owner, name))
        .withMethod("get")
        .withHeaders("Accept" -> "application/json")
        .withRequestTimeout(10 seconds)
        .get()
        .map { response =>
          RepoFetchResults(id, response.json.as[Repo])
        }
        .recover { case e => e.printStackTrace() }
        .pipeTo(self)

      context.become(waiting)
    }
    case _ => ()
  }

  def waiting: Receive = LoggingReceive {
    case msg@RepoFetchResults(id, result) => {
      context.parent ! msg
      context.become(active)
    }
    case _ => ()
  }

}
