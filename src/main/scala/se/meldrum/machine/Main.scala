package se.meldrum.machine

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import se.meldrum.machine.http.Server


object Main extends App {
  implicit val system = ActorSystem("machine")
  implicit val materializer = ActorMaterializer()
  val server = new Server
  server.run()
  println("Running main")
}
