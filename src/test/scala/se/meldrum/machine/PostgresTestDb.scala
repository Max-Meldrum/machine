package se.meldrum.machine

import com.typesafe.config.ConfigFactory
import de.flapdoodle.embed.process.runtime.Network._
import ru.yandex.qatools.embed.postgresql.PostgresStarter
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig.{Credentials, Net, Storage, Timeout}
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig
import ru.yandex.qatools.embed.postgresql.distribution.Version
import se.meldrum.machine.db.Schema
import slick.driver.PostgresDriver.api._

// Cred to ArchDev
// Slightly changed

object PostgresTestDb {

  val host = getLocalHost.getHostAddress
  val port = ConfigFactory.load().getInt("postgres-test.port")
  val dbName = ConfigFactory.load().getString("postgres-test.dbname")
  val user = ConfigFactory.load().getString("postgres-test.user")
  val password = ConfigFactory.load().getString("postgres-test.password")
  val url = ConfigFactory.load().getString("postgres-test.url")


  lazy val dbProcess = {
    val psqlConfig = new PostgresConfig(
      Version.V9_4_4, new Net(host, port),
      new Storage(dbName), new Timeout(),
      new Credentials(user, password)
    )
    val psqlInstance = PostgresStarter.getDefaultInstance

    val process = psqlInstance.prepare(psqlConfig).start()
    implicit val db = Database.forConfig("postgres-test")
    val schema = new Schema()
    schema.create()
    process
  }
}
