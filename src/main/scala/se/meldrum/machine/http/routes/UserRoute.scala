package se.meldrum.machine.http.routes

import akka.http.scaladsl.server.Directives._


class UserRoute {

  val route =
    path("user") {
      get {
        complete("hej")
      }
    }

}
