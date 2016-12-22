package se.meldrum.machine.db.models

import org.joda.time.DateTime
import com.github.tototoshi.slick.PostgresJodaSupport._
import slick.driver.PostgresDriver.api._

case class Task(title: String,
                description: String,
                deadline: DateTime,
                priority: String,
                userID: Option[Int] = None)


class Tasks(tag: Tag) extends Table[Task](tag, "tasks") {
  def title = column[String]("title")
  def description = column[String]("description")
  def deadline = column[DateTime]("deadline")
  def priority = column[String]("priority")
  def userID = column[Int]("id")
  def * = (title, description, deadline, priority, userID.?) <> (Task.tupled, Task.unapply)

  def user = foreignKey("user_fk", userID, TableQuery[Users])(_.id)
}

