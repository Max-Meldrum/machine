package se.meldrum.machine.http

import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}
import se.meldrum.machine.db.models.Task

object JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  // To make Joda DateTime available
  // cred to suin
  implicit object DateTimeJsonFormat extends RootJsonFormat[DateTime] {
    private lazy val format = ISODateTimeFormat.dateTimeNoMillis()
    def write(datetime: DateTime): JsValue = JsString(format.print(datetime.withZone(DateTimeZone.UTC)))
    def read(json: JsValue): DateTime = json match {
      case JsString(x) => format.parseDateTime(x)
      case x           => deserializationError("Expected DateTime as JsString, but got " + x)
    }
  }
  implicit val taskFormat = jsonFormat5(Task)
  implicit val userCreationFormat = jsonFormat3(UserCreation)
  implicit val userNamesFormat = jsonFormat1(UserNames)
  implicit val userRemovalFormat = jsonFormat2(UserRemoval)
}
