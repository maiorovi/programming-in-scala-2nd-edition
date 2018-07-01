package chapter14

class Other {
  def describe(x: Any) = x match {
    case 5 => "five"
    case true => "truth"
    case "hello" => "hi!"
    case Nil => "the empty list"
    case _ => "something else"
  }
}

object Demo2135 {
  def main(args: Array[String]):Unit = {
    val other = new Other()

    println(other.describe(5))
    println(other.describe(true))
    println(other.describe("hello"))
    println(other.describe(Nil))
  }
}

