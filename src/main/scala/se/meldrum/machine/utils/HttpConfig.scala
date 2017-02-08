package se.meldrum.machine.utils



trait HttpConfig extends Config {
  val interface = config.getString("http.interface")
  val port = config.getInt("http.port")
}
