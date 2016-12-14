package se.meldrum.machine.db

import slick.driver.PostgresDriver.api._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Schema extends DbComponent with DbQueries {

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
