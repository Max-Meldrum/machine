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


  def userJsonRequest(name: String, pass: String, email: String): ByteString =
    ByteString(
      s"""
       |{
       |    "name":"$name",
       |    "password":"$pass",
       |    "email":"$email"
       |}
        """.stripMargin)


  def createTestUsers(): Seq[HttpRequest] = {
    val userOne = userJsonRequest("testuser", "secret", "test@meldrum.se")
    val userTwo = userJsonRequest("testuser2", "secret", "test2@meldrum.se")
    val userThree = userJsonRequest("testuser3", "secret", "test3@meldrum.se")

    val requests = Seq(
      postRequest("/v1/user/create", userOne),
      postRequest("/v1/user/create", userTwo),
      postRequest("/v1/user/create", userThree)
    )
    requests
  }
}
