package se.meldrum.machine.http.routes

import akka.http.scaladsl.server.Directives._
import se.meldrum.machine.dao.TaskDao
import se.meldrum.machine.db.models.Task
import slick.driver.PostgresDriver.api._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class TaskRoute(implicit db: Database, implicit val ec: ExecutionContext) {

  private val dao = new TaskDao()

  import se.meldrum.machine.http.JsonSupport._

  val route =
    pathPrefix("task") {
      path("create") {
        post {
          entity(as[Task]) { task =>
            complete(createTask(task))
          }
        }
      }
    }

  private def createTask(t: Task): Future[String] = {
    val result = dao.create(t)
      .map {
        case Success(t) => "Created task"
        case Failure(e) => e.getMessage
      }
    result
  }
}
