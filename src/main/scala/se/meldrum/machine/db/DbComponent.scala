package se.meldrum.machine.db

import slick.driver.PostgresDriver.api._

trait DbComponent {
  val db = Database.forConfig("postgres")
}
