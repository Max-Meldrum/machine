package se.meldrum.machine.dao

import se.meldrum.machine.db.UserComponent
import se.meldrum.machine.db.models.User
import se.meldrum.machine.http.UserNames
import slick.driver.PostgresDriver.api._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class UserDao(implicit db: Database, implicit val ec: ExecutionContext) extends UserComponent {

  def create(user :User): Future[Try[User]] = {
    val dbAction = (users returning users.map(_.id) += user).map(id =>
    user.copy(id = Some(id)))

    db.run(dbAction.asTry)
  }

  def getUserNames(): Future[UserNames] = db
    .run(users.result)
    .map(_.map(_.name))
    .map(f => UserNames(f))
}
