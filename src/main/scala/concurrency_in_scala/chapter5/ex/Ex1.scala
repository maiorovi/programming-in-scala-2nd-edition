package concurrency_in_scala.chapter5.ex

import concurrency_in_scala.chapter2.log

object Ex1 extends App {
  @volatile var dummy: Any = _

  def allocate(): Unit = dummy = new Object()

  def warmUp(body: => Unit, times: Int) = {
    for(x <- 0 until times) body
  }

  def measureTime(body: => Unit): Long = {
    warmUp(body, 1000000)
    val arr = new Array[Long](10000000)

    for (x <- 0 until 10000000) {
      arr(x) = doMeasurement(body)
    }

    return arr.sum / arr.length
  }

  def doMeasurement(body: => Unit): Long = {
    val start = System.nanoTime()

    body

    val end = System.nanoTime()

    end - start
  }



  println(measureTime(allocate()))

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
