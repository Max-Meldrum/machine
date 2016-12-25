package se.meldrum.machine.http

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import se.meldrum.machine.http.routes.{TaskRoute, UserRoute}
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext

class RestService(implicit val system: ActorSystem,
                  implicit val materializer: ActorMaterializer,
                  implicit val db: Database,
                  implicit val ec: ExecutionContext) {

  val userRoute = new UserRoute()
  val taskRoute = new TaskRoute()

  val route =
    pathPrefix("v1") {
      userRoute.route~
      taskRoute.route
    }
}
