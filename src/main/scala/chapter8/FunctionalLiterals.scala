package chapter8

object FunctionalLiterals {
  def main(args: Array[String]): Unit = {
    val test: Int => Int = (x:Int) => x + 1
    val test1: Int => Int = _ + 1

    val oneMoreIncrease = (x:Int) => {
      println("We")
      println("Are")
      println("Here")
      x + 1
    }

    println(test(10))

    oneMoreIncrease(1)

    val summ: (Int, Int) => Int = _ + _
    val summ1 = (_: Int) + (_:Int)

  }
}
