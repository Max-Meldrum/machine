package se.meldrum.machine.db

import se.meldrum.machine.db.models.{Tasks, Users}
import slick.lifted.TableQuery

trait DbQueries {
  val users = TableQuery[Users]
  val tasks = TableQuery[Tasks]
}
