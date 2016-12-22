package se.meldrum.machine

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import se.meldrum.machine.db.Schema
import se.meldrum.machine.http.RestService
import slick.driver.PostgresDriver.api._

object Main extends App {

  implicit val db = Database.forConfig("postgres")

  if (args.length > 0)
    cmd(args(0))
  else
    startServer

  def startServer(): Unit = {
    implicit val system = ActorSystem("machine")
    implicit val materializer = ActorMaterializer()
    val restService = new RestService()
    Http().bindAndHandle(restService.route, "localhost", 8080)
  }

  def cmd(arg: String): Unit = {
    val schema = new Schema()

    def go(arg: String): Unit = arg match {
      case "reset" => schema.reset()
      case "create" => schema.create()
      case _ => println("Invalid argument!")
    }
    go(arg)
  }

}
