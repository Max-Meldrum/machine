package se.meldrum.machine

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import PostgresTestDb._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import se.meldrum.machine.http.RestService
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext

trait BaseSpec extends WordSpec with Matchers with ScalatestRouteTest {
  dbProcess.getProcessId
  implicit val db = Database.forConfig("postgres-test")
  implicit val sys = ActorSystem("machine")
  implicit val ec = ExecutionContext
  implicit val mat = ActorMaterializer()
  val restService = new RestService()
  Http().bindAndHandle(restService.route, "localhost", 0)
}
