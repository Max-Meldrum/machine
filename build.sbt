name := "machine"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.0",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.0",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.1.1",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.2.0",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc4",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    exclude("org.scala-lang.modules", "scala-xml_2.11")
)