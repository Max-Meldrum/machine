package se.meldrum.machine.http


case class UserCreation(name: String, password: String, email: String) {
  require(name.length < 50 && !name.isEmpty, "Non valid name")
  require(password.length < 50 && !password.isEmpty, "Non valid password")
  require(email.length < 50 && email.contains("@"), "Non valid email")
}

case class UserNames(names: Seq[String])

