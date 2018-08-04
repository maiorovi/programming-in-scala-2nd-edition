package concurrency_in_scala.chapter5

import scala.util.Random
import concurrency_in_scala.chapter2._

import scala.collection.GenSet

object ParBasic extends App {
  val numbers = Random.shuffle(Vector.tabulate(5000000)(i => i))
  val seqtime = timed { numbers.max }
  log(s"Sequential time $seqtime ms")
  val partime = timed { numbers.par.max }
  log(s"Parallel time $partime ms")
}


object ParSideEffectsIncorrect extends App {
  def intersectionSize(a: GenSet[Int], b: GenSet[Int]): Int = {
    var total = 0
    for (x <- a) if (b contains x) total += 1
    total
  }

  val a = (0 until 10000).toSet
  val b =  (0 until 10000 by 4).toSet

  val seqres = intersectionSize(a, b)
  val parres = intersectionSize(a.par, b.par)

  log(s"Sequential result - $seqres")
  log(s"Parallel result   - $parres")
}