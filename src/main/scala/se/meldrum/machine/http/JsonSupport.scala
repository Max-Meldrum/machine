package se.meldrum.machine.http

import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

object JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  //implicit val taskFormat = jsonFormat3(Task)
}
