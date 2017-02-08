package se.meldrum.machine.utils



trait HttpConfig extends Config {
  val interface = config.getString("http.host")
  val port = config.getInt("http.port")
}
