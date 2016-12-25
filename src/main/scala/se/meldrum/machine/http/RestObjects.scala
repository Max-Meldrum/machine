package se.meldrum.machine.http

case class TaskCreation(title: String, description: String, priority: String) {
  require(title.length < 200 && !title.isEmpty, "Non valid title")
  require(description.length < 1500 && !description.isEmpty, "Non valid description")
  require(priority == "High" || priority == "Medium" || priority == "Low")
}

case class UserCreation(name: String, password: String, email: String) {
  require(name.length < 50 && !name.isEmpty, "Non valid name")
  require(password.length < 50 && !password.isEmpty, "Non valid password")
  require(email.length < 50 && email.contains("@"), "Non valid email")
}

case class UserNames(names: Seq[String])

