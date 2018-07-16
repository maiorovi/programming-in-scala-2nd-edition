package hackerrank

import java.io._
import java.math._
import java.security._
import java.text._
import java.util.concurrent._
import java.util.function._
import java.util.regex._
import java.util.stream._


object TheTimeInWords {

  private val digitToStringMapping: Map[Int, String] = Map[Int, String](
    1 -> "one",
    2 -> "two",
    3 -> "three",
    4 -> "four",
    5 -> "five",
    6 -> "six",
    7 -> "seven",
    8 -> "eight",
    9 -> "nine",
    10 -> "ten",
    11 -> "eleven",
    12 -> "twelve",
    13 -> "thirteen",
    14 -> "fourteen",
    15 -> "fifteen",
    16 -> "sixteen",
    17 -> "seventeen",
    18 -> "eighteen",
    19 -> "nineteen")

  // Complete the timeInWords function below.
  def timeInWords(h: Int, m: Int): String = {
    val hour = deriveHour(h, m)

    if (m == 0) {
      s"$hour o' clock"
    } else {
      s"${deriveMinutes(m)} ${deriveToOrPast(m)} $hour"
    }
  }

  private def deriveHour(h: Int, m: Int):String = digitToStringMapping(if(m > 30) moduloOrDefault(h+1, 12) else h)

  private def moduloOrDefault(x: Int, default: Int): Int = if (x % 12 == 0) default else x % 12


  private def deriveToOrPast(m: Int): String = if (m <= 30) "past" else "to"

  private def deriveMinutes(m: Int): String = m match {
    case 15 => "quarter"
    case 30 => "half"
    case z if z == 45 => "quarter"
    case z if z > 30 => minutesToString(60 - z)
    case z if z < 30 => minutesToString(z)
  }

  private def minutesToString(m: Int): String = m match {
    case z if z == 20 => s"twenty minutes"
    case z if z > 20 => s"twenty ${digitToStringMapping(z-20)} minutes"
    case z if z == 1 => s"${digitToStringMapping(z)} minute"
    case z => s"${digitToStringMapping(z)} minutes"
  }

  def main(args: Array[String]) {
    val stdin = scala.io.StdIn

    val printWriter = new PrintWriter("/app/Projects/Programming-In-Scala/src/main/scala/hackerrank/output.txt")

    val h = stdin.readLine.trim.toInt

    val m = stdin.readLine.trim.toInt

    val result = timeInWords(h, m)

    printWriter.println(result)

    printWriter.close()
  }
}
