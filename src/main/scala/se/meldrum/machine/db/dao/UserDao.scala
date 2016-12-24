package se.meldrum.machine.db.dao

import se.meldrum.machine.db.models.{User, Users}
import slick.lifted.TableQuery
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try


class UserDao(implicit db: Database) {

  val users = TableQuery[Users]

  def create(user :User): Future[Try[User]] = {
    val dbAction = (users returning users.map(_.id) += user).map(id =>
    user.copy(id = Some(id)))

    db.run(dbAction.asTry)
  }

  def getUserNames(): Future[Seq[String]] = db
    .run(users.result)
    .map(_.map(_.name))
}
