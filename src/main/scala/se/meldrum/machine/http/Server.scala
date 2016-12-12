package se.meldrum.machine.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

class Server(implicit val system: ActorSystem,
             implicit val materializer: ActorMaterializer
            ) extends RestService {

  def run() = Http().bindAndHandle(route, "localhost", 8080)
}
