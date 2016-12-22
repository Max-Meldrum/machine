package se.meldrum.machine.db.models

import slick.driver.PostgresDriver.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class User(id: Option[Int] = None, name: String, password: String, email: String)

class Users(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def password = column[String]("password")
  def email = column[String]("email")
  def * = (id.?, name, password, email) <> (User.tupled, User.unapply)

  def idx = index("unique_name", (name), unique = true)
}

class UserDao(implicit db: Database)  {

  val users = TableQuery[Users]

  def create(user: User): Future[User] = db
    .run(users returning users.map(_.id) += user)
    .map(id => user.copy(id = Some(id)))

}
