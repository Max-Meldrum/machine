package se.meldrum.machine

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import se.meldrum.machine.db.Schema
import se.meldrum.machine.http.RestService
import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext

object Main extends App {

  implicit val db = Database.forConfig("postgres")

  if (args.length > 0)
    cmd(args(0))
  else
    startServer

  private def startServer(): Unit = {
    implicit val system = ActorSystem("machine")
    implicit val ec: ExecutionContext = system.dispatcher
    implicit val materializer = ActorMaterializer()
    val restService = new RestService()
    Http().bindAndHandle(restService.route, "localhost", 8080)
  }

  private def cmd(arg: String): Unit = {
    val schema = new Schema()

    def go(arg: String): Unit = arg match {
      case "reset" => schema.reset()
      case "create" => schema.create()
      case _ => println("Invalid argument!")
    }
    go(arg)
  }

}
