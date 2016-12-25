package se.meldrum.machine.db

import se.meldrum.machine.db.models.Tasks
import slick.lifted.TableQuery


trait TaskComponent {
  val tasks = TableQuery[Tasks]
}
