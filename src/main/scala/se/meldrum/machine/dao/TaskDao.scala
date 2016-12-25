package se.meldrum.machine.dao

import se.meldrum.machine.db.TaskComponent
import se.meldrum.machine.db.models.Task
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future
import scala.util.Try

class TaskDao(implicit db: Database) extends TaskComponent {

  def create(task: Task): Future[Try[Task]] = {
    val dbAction = tasks returning tasks += task
    db.run(dbAction.asTry)
  }

  def getTasks(id: Int): Future[Seq[Task]] =
    db.run(tasks.filter(_.userID === id).result)

}
