package se.meldrum.machine.db.dao

import se.meldrum.machine.db.models.{User, Users}
import slick.lifted.TableQuery
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class UserDao(implicit db: Database) {

  val users = TableQuery[Users]

  def create(user: User): Future[User] = db
    .run(users returning users.map(_.id) += user)
    .map(id => user.copy(id = Some(id)))

  def getUserNames(): Future[Seq[String]] = db
    .run(users.result)
    .map(_.map(_.name))
}
