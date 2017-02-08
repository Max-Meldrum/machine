package se.meldrum.machine.dao

import se.meldrum.machine.db.components.TaskComponent
import se.meldrum.machine.db.models.Task
import slick.driver.PostgresDriver.api._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class TaskDao(implicit db: Database) extends TaskComponent {

  def create(task: Task)(implicit e: ExecutionContext): Future[Try[Task]] = {
    val dbAction = tasks returning tasks += task
    db.run(dbAction.asTry)
  }

  def getTasks(id: Int)(implicit e: ExecutionContext): Future[Seq[Task]] =
    db.run(tasks.filter(_.userID === id).result)

}
