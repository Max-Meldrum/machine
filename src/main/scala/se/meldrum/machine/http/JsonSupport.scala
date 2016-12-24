package se.meldrum.machine.http

import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

object JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val taskCreationFormat = jsonFormat3(TaskCreation)
  implicit val userCreationFormat = jsonFormat3(UserCreation)
  implicit val userNamesFormat = jsonFormat1(UserNames)
}
