package se.meldrum.machine

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import PostgresTestDb._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import se.meldrum.machine.http.RestService
import slick.driver.PostgresDriver.api._

trait BaseSpec extends WordSpec with Matchers with ScalatestRouteTest {
  dbProcess.getProcessId
  implicit val db = Database.forConfig("postgres-test")
  implicit val systemx = ActorSystem("machine")
  implicit val materializerz = ActorMaterializer()
  val restService = new RestService()
  Http().bindAndHandle(restService.route, "localhost", 8080)
}
