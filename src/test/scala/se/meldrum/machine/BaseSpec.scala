package se.meldrum.machine

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import PostgresTestDb._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes}
import akka.stream.ActorMaterializer
import akka.util.ByteString
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
  val route = restService.route
  Http().bindAndHandle(restService.route, "localhost", 0)

  def postRequest(path: String, json: ByteString): HttpRequest =
    HttpRequest(HttpMethods.POST,
      uri = path,
      entity = HttpEntity(MediaTypes.`application/json`, json)
    )

  def createTestUsers(): Seq[HttpRequest] = {
    val userOne = ByteString(
      s"""
         |{
         |    "name":"testuser",
         |    "password":"secret",
         |    "email":"test@meldrum.se"
         |}
        """.stripMargin)

    val userTwo = ByteString(
      s"""
         |{
         |    "name":"testuser2",
         |    "password":"secret",
         |    "email":"test2@meldrum.se"
         |}
        """.stripMargin)

    val userThree = ByteString(
      s"""
         |{
         |    "name":"testuser3",
         |    "password":"secret",
         |    "email":"test3@meldrum.se"
         |}
        """.stripMargin)

    val requests = Seq(
      postRequest("/v1/user/create", userOne),
      postRequest("/v1/user/create", userTwo),
      postRequest("/v1/user/create", userThree)
    )
    requests
  }
}
