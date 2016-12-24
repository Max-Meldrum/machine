package se.meldrum.machine.http.routes

import akka.http.scaladsl.server.Directives._
import org.postgresql.util.PSQLException
import se.meldrum.machine.db.dao.UserDao
import se.meldrum.machine.db.models.User
import se.meldrum.machine.http.UserCreation
import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

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
            import com.github.t3hnar.bcrypt._
            val cryptedPass = user.password.bcrypt
            val u = User(None, user.name, cryptedPass, user.email)
            complete(createUser(u))
            }
          }
        }
      }

  def createUser(u: User): Future[String] = {
    val result = dao.create(u)
      .map {
        case Success(u) => "User created"
        case Failure(e: PSQLException) if e.getSQLState == "23505" => "User already exists"
        case Failure(e) => e.getMessage
      }
    result
  }

}
