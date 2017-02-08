package se.meldrum.machine.utils

import com.typesafe.config.ConfigFactory


trait Config {
  val config = ConfigFactory.load()
}
