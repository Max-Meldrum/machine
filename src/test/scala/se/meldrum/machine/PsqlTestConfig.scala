package se.meldrum.machine

import se.meldrum.machine.utils.Config


trait PsqlTestConfig extends Config {
  val host = config.getString("postgres-test.host")
  val port = config.getInt("postgres-test.port")
  val dbName = config.getString("postgres-test.dbname")
  val user = config.getString("postgres-test.user")
  val password = config.getString("postgres-test.password")
  val url = config.getString("postgres-test.url")
}
