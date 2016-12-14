package se.meldrum.machine.http

case class TaskCreation(title: String, description: String, priority: String) {
  require(priority == "High" || priority == "Medium" || priority == "Low")
}

