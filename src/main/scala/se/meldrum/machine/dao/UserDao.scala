package se.meldrum.machine.dao

import se.meldrum.machine.db.components.UserComponent
import se.meldrum.machine.db.models.User
import se.meldrum.machine.http.UserNames
import slick.driver.PostgresDriver.api._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class UserDao(implicit db: Database) extends UserComponent {

  def create(user :User)(implicit e: ExecutionContext): Future[Try[User]] = {
    val dbAction = (users returning users.map(_.id) += user).map(id =>
    user.copy(id = Some(id)))

    db.run(dbAction.asTry)
  }

  def delete(username: String)(implicit e: ExecutionContext): Future[Try[Int]] = {
    val dbAction = users.filter(_.name === username).delete
    db.run(dbAction.asTry)
  }

  def getUserNames()(implicit e: ExecutionContext): Future[UserNames] = db
    .run(users.result)
    .map(_.map(_.name))
    .map(f => UserNames(f))
}
