package se.meldrum.machine.http

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import se.meldrum.machine.http.routes.UserRoute

trait RestService {
  implicit val system:ActorSystem
  implicit val materializer:ActorMaterializer

  import JsonSupport._

  val userRoute = new UserRoute()

  val route =
    pathPrefix("v1") {
      userRoute.route
    }

  /*
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
  */
}
