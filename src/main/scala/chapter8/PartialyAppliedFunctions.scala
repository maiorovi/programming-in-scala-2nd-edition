package chapter8

object PartialyAppliedFunctions {

  def main(args: Array[String]): Unit = {
    val f1: (Int, Int, Int) => Int = sum
    val f5 = sum _
    val f2: (Int, Int) => Int = sum (1, _, _)
    val f3: Int => Int = sum (1, 2, _)
    val f4: (Int, Int, Int) => Int = (x,y,z) => sum(x,y,z)

  }

  def sum(a: Int, b: Int, c: Int): Int = a + b + c
}
