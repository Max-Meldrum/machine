package se.meldrum.machine.http.routes

import akka.http.scaladsl.server.Directives._
import se.meldrum.machine.db.models.{User, UserDao}
import se.meldrum.machine.http.UserCreation
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserRoute(implicit db: Database) {

  val dao = new UserDao()

  import se.meldrum.machine.http.JsonSupport._

  val route =
    pathPrefix("user") {
      path("create") {
        post {
          entity(as[UserCreation]) { user=>
            val u = User(None, user.name, user.password, user.email)
            // Getting problems on duplicated user name "FIX"
            complete(createUser(u))
          }
        }~
        get {
          complete("haj")
        }
      }
    }


  def createUser(user: User): Future[String] =
    dao.create(user)
    .map(_.id)
    .map(o => o match {
      case Some(i) => "User created"
      case None => "ERROR!"
    })



}
