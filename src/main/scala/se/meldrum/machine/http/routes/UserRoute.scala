package se.meldrum.machine.http.routes

import akka.http.scaladsl.server.Directives._
import org.postgresql.util.PSQLException
import se.meldrum.machine.dao.UserDao
import se.meldrum.machine.db.models.User
import se.meldrum.machine.http.UserCreation
import slick.driver.PostgresDriver.api._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class UserRoute(implicit db: Database, implicit val ec: ExecutionContext) {

  private val dao = new UserDao()

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
        }~
      path("delete") {
        post {
          entity(as[String]) { username =>
            complete(deleteUser(username))
          }
        }
      }
    }

  private def createUser(u: User): Future[String] = {
    val result = dao.create(u).map {
        case Success(u) => "User created"
        case Failure(e: PSQLException) if e.getSQLState == "23505" => "User already exists"
        case Failure(e) => e.getMessage
      }
    result
  }

  private def deleteUser(user: String): Future[String] = {
    val result = dao.delete(user).map {
      case Success(v) => "returned " + v
      case Failure(e) => e.getMessage
    }
    result
  }

}
