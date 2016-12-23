package se.meldrum.machine

import akka.http.scaladsl.model._
import akka.util.ByteString


class UserSpec extends BaseSpec {

  val route = restService.route

  "User Spec" should {

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

    "be able to create test user" in {
      val jsonRequest = ByteString(
        s"""
           |{
           |    "name":"testuser",
           |    "password":"hashedsecret",
           |    "email":"test@meldrum.se"
           |}
        """.stripMargin)

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/v1/user/create",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest)
      )

      postRequest ~> route ~> check {
        responseAs[String] shouldEqual "User created"
      }
    }

    "be able to retreive test user when it has been inserted" in {
      Get("/v1/user") ~> route ~> check {
        responseAs[String].contains("testuser") shouldEqual true
      }
    }


  }
}
