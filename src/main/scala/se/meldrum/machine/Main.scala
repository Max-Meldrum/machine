package se.meldrum.machine

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import se.meldrum.machine.db.Schema
import se.meldrum.machine.http.Server

object Main extends App {

  if (args.length > 0)
    cmd(args(0))
  else
    startServer

  def startServer(): Unit = {
    implicit val system = ActorSystem("machine")
    implicit val materializer = ActorMaterializer()
    val server = new Server
    server.run()
  }

  def cmd(arg: String): Unit = arg match {
    case "reset" => Schema.reset()
    case "create" => Schema.create()
    case _ => println("Invalid argument!")
  }
}
