package se.meldrum.machine

import akka.http.scaladsl.model._
import akka.util.ByteString
import akka.http.scaladsl.unmarshalling.Unmarshaller._
import se.meldrum.machine.http.UserNames

class UserSpec extends BaseSpec {

  import se.meldrum.machine.http.JsonSupport._

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

    "create test users" in {
      val postRequests = createTestUsers()

      postRequests.map(req =>
      req ~> route ~> check {
        responseAs[String] shouldEqual "User created"
      })
    }

    "notify of duplicated user" in {
      val postRequest = createExistingUser()
      postRequest ~> route ~> check {
        responseAs[String] shouldEqual "User already exists"
      }
    }

    "retreive test users" in {
      Get("/v1/user") ~> route ~> check {
          responseAs[UserNames] shouldEqual UserNames(Seq("testuser", "testuser2", "testuser3"))
      }
    }
  }

  private def createExistingUser(): HttpRequest = {
    val jsonRequest = ByteString(
      s"""
         |{
         |    "name":"testuser",
         |    "password":"secret",
         |    "email":"test@meldrum.se"
         |}
        """.stripMargin)

    postRequest("/v1/user/create", jsonRequest)
  }
}
