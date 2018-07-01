val capitals = Map("France" -> "Paris",
  "Japan" -> "Tokyo")


capitals get "France" match {
  case Some(v) => v
  case None => "non"
}

case class User(firstName: String,
                lastName: String,
                score: Int)


val u = User("Iegor", "Maiorov", 50)

val User(name, lastName, score) = u

def withDefault: Option[Int] => Int = {
  case Some(x) => x
  case None => 0
}

withDefault(Some(10))
withDefault(None)

val f: Function[Option[Int], Int] = {
  case Some(x) => x
  case None => 0
}


for ((country, city) <- capitals)
  println("The capital of " + country + " is " + city)


val results = List(Some("apple"), None, Some("orange"))


for(Some(fruit) <- results) println(fruit)


val fruit = List("apple", "orange")