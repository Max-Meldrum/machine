package se.meldrum.machine

class TaskSpec extends BaseSpec {

  private val route = restService.route

  "Task route " should {
    "not handle GET requests on invalid paths" in {
      Get("/nonexistingpath") ~> route ~> check {
        handled shouldBe false
      }
    }
  }

}
