package se.meldrum.machine

import akka.http.scaladsl.model._
import akka.util.ByteString
import akka.http.scaladsl.unmarshalling.Unmarshaller._
import se.meldrum.machine.http.UserNames

class UserSpec extends BaseSpec {

  import se.meldrum.machine.http.JsonSupport._
  val route = restService.route

  "User route" should {

    "not handle GET requests on invalid paths" in {
      Get("/nonexistingpath") ~> route ~> check {
        handled shouldBe false
      }
    }

    "confirm that there is no test user when db is empty" in {
      Get("/v1/user") ~> route ~> check {
        responseAs[UserNames] shouldEqual UserNames(Seq.empty[String])
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
          responseAs[UserNames] shouldEqual UserNames(Seq("testuser"))
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
