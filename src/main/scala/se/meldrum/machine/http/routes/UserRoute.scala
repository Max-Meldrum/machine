package se.meldrum.machine.http.routes

import akka.http.scaladsl.server.Directives._
import se.meldrum.machine.db.dao.UserDao
import se.meldrum.machine.db.models.User
import se.meldrum.machine.http.UserCreation
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global

class UserRoute(implicit db: Database) {

  val dao = new UserDao()

  import se.meldrum.machine.http.JsonSupport._

  val route =
    pathPrefix("user") {
      get {
        complete(dao.getUserNames())
      }~
      path("create") {
        post {
          entity(as[UserCreation]) { user=>
            val u = User(None, user.name, user.password, user.email)
            // Getting problems on duplicated user name "FIX"
            onSuccess(dao.create(u).map(_.id)) {
              case Some(i) => complete("User created")
              case None => complete("ERROR")
            }
          }
        }
      }
    }


}
