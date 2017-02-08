package se.meldrum.machine

import ru.yandex.qatools.embed.postgresql.PostgresStarter
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig.{Credentials, Net, Storage, Timeout}
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig
import ru.yandex.qatools.embed.postgresql.distribution.Version
import se.meldrum.machine.db.Schema
import slick.driver.PostgresDriver.api._

// Cred to ArchDev
// Slightly changed
object PostgresTestDb extends PsqlTestConfig {

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
