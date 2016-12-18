package se.meldrum.machine

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import se.meldrum.machine.http.RestService


class UserSpec extends WordSpec with Matchers with ScalatestRouteTest with RestService {

  "User route" should {

    "not handle GET requests on invalid paths" in {
      Get("/nonexistingpath") ~> route ~> check {
        handled shouldBe false
      }
    }


  }
}
