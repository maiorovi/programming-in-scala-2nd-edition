package chapter7

import java.io.{BufferedReader, InputStreamReader}

object BreakableDemo {
  import scala.util.control.Breaks._

  def main(args: Array[String]): Unit = {
    val in  = new BufferedReader(new InputStreamReader(System.in))

    breakable {
      while (true) {
        println("? ")
        if (in.readLine() == "") break
      }
    }
  }


}
