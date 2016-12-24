package se.meldrum.machine

import akka.http.scaladsl.model._
import akka.util.ByteString


class UserSpec extends BaseSpec {

  val route = restService.route

  "User route" should {

    "not handle GET requests on invalid paths" in {
      Get("/nonexistingpath") ~> route ~> check {
        handled shouldBe false
      }
    }

    "confirm that there is no test user when db is empty" in {
      Get("/v1/user") ~> route ~> check {
        responseAs[String].contains("testuser") shouldEqual false
      }
    }

    "create test user" in {
      val postRequest = createTestUser()
      postRequest ~> route ~> check {
        responseAs[String] shouldEqual "User created"
      }
    }

    "notify of duplicated user" in {
      val postRequest = createTestUser()
      postRequest ~> route ~> check {
        responseAs[String] shouldEqual "User already exists"
      }
    }

    "retreive test user when one has been inserted" in {
      Get("/v1/user") ~> route ~> check {
        responseAs[String].contains("testuser") shouldEqual true
      }
    }
  }

  def createTestUser(): HttpRequest = {
    val jsonRequest = ByteString(
      s"""
         |{
         |    "name":"testuser",
         |    "password":"hashedsecret",
         |    "email":"test@meldrum.se"
         |}
        """.stripMargin)

    HttpRequest(
      HttpMethods.POST,
      uri = "/v1/user/create",
      entity = HttpEntity(MediaTypes.`application/json`, jsonRequest)
    )
  }
}
