package se.meldrum.machine.http.routes

import akka.http.scaladsl.server.Directives._
import se.meldrum.machine.http.TaskCreation

class TaskRoute {

  import se.meldrum.machine.http.JsonSupport._

  val route =
    pathPrefix("task") {
      path("create") {
        get {
          complete("hej")
        }
        post {
          entity(as[TaskCreation]) { tc =>
            complete(tc.title)
          }
        }
      }
    }
}
