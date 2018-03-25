package chapter7

import scala.io.StdIn

object MatchExpression {
  def main(args: Array[String]): Unit = {
    val str = StdIn.readLine()

    val result = str match {
      case "salt" => println("pepper")
      case "chips" => println("bacon")
      case _ => println("huh?")
    }

    println(result)
  }
}
