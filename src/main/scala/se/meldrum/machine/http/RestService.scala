package se.meldrum.machine.http

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._

trait RestService {
  implicit val system:ActorSystem
  implicit val materializer:ActorMaterializer

  import JsonSupport._

  val route =
    get {
      path("test") {
        complete("test")
      }
    } ~
  post {
    path("addtask") {
      entity(as[TaskCreation]) {
        e => {
          complete("hej")
        }
      }
    }
  }

}
