package se.meldrum.machine.db.models

import slick.driver.PostgresDriver.api._

case class User(id: Option[Int], name: String, password: String, email: String)

class Users(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def password = column[String]("password")
  def email = column[String]("email")
  def * = (id.?, name, password, email) <> (User.tupled, User.unapply)

  def idx = index("unique_name", (name), unique = true)
}
