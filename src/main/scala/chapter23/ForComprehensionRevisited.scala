package chapter23

object ForComprehensionRevisited extends App {

  case class Person(name: String,
                    isMale: Boolean,
                    children: Person*)

  val lara = Person("Lara", false)
  val bob = Person("Bob", true)
  val julie = Person("Julie", false, lara, bob)
  val persons = List(lara, bob, julie)

  private val tuples: List[(String, String)] = persons.filter(p => !p.isMale)
    .flatMap(c => c.children.map(ch => (c.name, ch.name)))


  println(tuples)


  val tuples1 = for {p <- persons
                     if !p.isMale
                     c <- p.children } yield (p.name, c.name)

  println(tuples)

}


object QueryingWithForExpression extends App {
  case class Book(title: String, authors: String*)

}

