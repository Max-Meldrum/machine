package se.meldrum.machine.http

case class TaskCreation(title: String, description: String, priority: String) {
  require(title.length < 200)
  require(description.length < 1500)
  require(priority.length < 7)
  require(priority == "High" || priority == "Medium" || priority == "Low")
}

case class UserCreation(name: String, password: String, email: String) {
  require(name.length < 50)
  require(password.length < 50)
  require(email.length < 50 && email.contains("@"))
}

case class UserNames(names: Seq[String])

