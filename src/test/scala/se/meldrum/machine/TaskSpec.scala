package se.meldrum.machine

import se.meldrum.machine.db.models.Task

class TaskSpec extends BaseSpec {

  import se.meldrum.machine.http.JsonSupport._

  "Task route " should {
    "not handle GET requests on invalid paths" in {
      Get("/nonexistingpath") ~> route ~> check {
        handled shouldBe false
      }
    }

    /*
    "confirm that there are no tasks when db is empty" in {
      Get("/v1/task") ~> route ~> check {
        responseAs[Task] shouldEqual List[Task]()
      }
    }
    */
  }

}
