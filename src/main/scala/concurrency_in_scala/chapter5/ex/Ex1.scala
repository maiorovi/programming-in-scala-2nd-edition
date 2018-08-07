package concurrency_in_scala.chapter5.ex

import concurrency_in_scala.chapter2.log
import concurrency_in_scala.chapter5.ex.Ex11.{i, summ}

object Ex1 extends App {
  @volatile var dummy: Any = _

  def buildObjects(count: Int): Double = {
    val start = System.nanoTime()
    var i = 0

    while(i < count) {
      dummy = new Object()
      i += 1
    }

    (System.nanoTime() - start) / count.toDouble
  }


  var i = 0
  var summ = 0D

  var timePrev = 0D

  while( i < 30 ) {
    val timeTaken = buildObjects(1000000)
    val diff = Math.abs(timeTaken - timePrev) / timeTaken * 100

    if (diff < 10) {
      i += 1
      summ += timeTaken
    } else {
      i = 0
      summ = timeTaken
    }

    timePrev = timeTaken
    log(s"time = ${timeTaken.toString} e = ${Math.round(diff)}, i = $i")
  }
    log(s"avg ${summ/(i+1)} ")
}

object Ex11 extends App {

  object Timed {

    @volatile
    var dummy: Any = _

    def buildObjects(count:Int) = {
      var i = 0
      val start = System.nanoTime
      while (i < count) {
        dummy = new Object
        i += 1
      }
      (System.nanoTime - start)/count.toDouble
    }

  }

  var i = 0
  var summ = 0D

  var timePrev = 0D
  while (i < 30) {

    val time = Timed.buildObjects(10000000)
    val e = Math.abs(time - timePrev)/time*100

    //check steady state
    if (e < 10) {
      i += 1
      summ += time
    } else {
      i = 0
      summ = time
    }

    timePrev = time
    log(s"time = ${time.toString} e = ${Math.round(e)}, i = $i")

  }

  log("----------------------------------------------------")
  log(s"avg = ${summ/(i+1)} nanoseconds")

}
