package se.meldrum.machine.db.components

import se.meldrum.machine.db.models.Users
import slick.lifted.TableQuery


trait UserComponent {
  val users = TableQuery[Users]
}
