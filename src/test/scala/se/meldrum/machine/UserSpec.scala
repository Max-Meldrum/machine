package se.meldrum.machine

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.scaladsl.{Sink, Source}
import akka.util.ByteString
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import se.meldrum.machine.db.models.{User, UserDao}

import scala.concurrent.Future
import scala.util.{Failure, Success}

class UserSpec extends BaseSpec with ScalaFutures {

  val route = restService.route
  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "User Spec" should {

    "not handle GET requests on invalid paths" in {
      Get("/nonexistingpath") ~> route ~> check {
        handled shouldBe false
      }
    }

    "respond haj" in {
      Get("/v1/user/create") ~> route ~> check {
        responseAs[String] shouldEqual "haj"
      }
    }

    /*
    "Be able to create user" in {
      val jsonRequest = ByteString(
        s"""
           |{
           |    "name":"test",
           |    "password":"hashedsecret",
           |    "email":"test@meldrum.se"
           |}
        """.stripMargin)

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/v1/user/create",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest)
      )

      val resp = sendRequest(postRequest)

      whenReady(resp) {result =>
        assert(result === "User bla")
      }


    }
  }
  */
  }

  def sendRequest(req: HttpRequest) =
    Source.single(req).via(
      Http(system).outgoingConnection(host = "localhost",  port = 8080)).runWith(Sink.head)

}
