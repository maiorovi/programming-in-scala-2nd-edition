package chapter12

trait Philosophical {

  def philosophize() = {
    println("I consume memory, therefore I am")
  }

}

class Animal
trait HasLegs

class Frog extends Animal with Philosophical with HasLegs {
  override def toString: String = "green"
}


object Demo1234 {
  def main(args: Array[String]): Unit = {
    val frog = new Frog

    frog.philosophize()
  }

}