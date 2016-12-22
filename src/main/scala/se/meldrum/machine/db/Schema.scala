package se.meldrum.machine.db

import se.meldrum.machine.db.models.{Tasks, Users}
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class Schema(implicit db: Database) {

  val users = TableQuery[Users]
  val tasks = TableQuery[Tasks]

  def reset(): Unit = {
    val r = DBIO.seq(
      (users.schema ++ tasks.schema).drop
    )
    Await.result(db.run(r), Duration.Inf)
  }

  def create(): Unit = {
    val c = DBIO.seq(
      (users.schema ++ tasks.schema).create
    )
    Await.result(db.run(c), Duration.Inf)
  }
}
