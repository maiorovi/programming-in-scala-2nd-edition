package chapter9

object Currying {

  def curriedSum(x:Int)(y:Int) = x + y

  def onePlus(x:Int) = curriedSum(x)_

  def twoPlus(x:Int) = curriedSum(x)_

  def twice(op: Double => Double, x:Double) = op(op(x))

}

object CurryingDemo {
  def main(args: Array[String]): Unit = {
    println(Currying.curriedSum(5)(10))

    println(Currying.onePlus(5))

    println { "Hello World!" }
  }
}
