package concurrency_in_scala.chapter4.ex

import java.util.{Timer, TimerTask}

import akka.util.LineNumbers.SourceFile

import scala.concurrent.{Await, Future, Promise}
import scala.io.{Source, StdIn}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, TimeUnit}
import scala.util.{Failure, Success}

object Ex1 {
  private val timer = new Timer(true)

  def stopTimer(t: Timer): Unit = {
    t.cancel()
    t.purge()
  }

  def timeout(p: Promise[String], delay: Int): Unit = {
    timer.schedule(new TimerTask {
      override def run(): Unit = {
        p trySuccess("sorry, timeout")
      }
    }, delay.toLong)
  }

  def main(args:Array[String]): Unit = {
    while(true) {
      val url = s"https://${StdIn.readLine}"

      val dotPrintTimer = new Timer(true)

      val p = Promise[String]

      val reader = Future {
        timeout(p, 2000)
        Source.fromURL(url).getLines().mkString("\n")
      } onComplete {
        case Success(value) => p trySuccess value
        case Failure(exception) => p trySuccess s"Failure ${exception.getMessage}"
      }

      val f = Future {
        println("Waiting for info to be retrieved ")
        timeOutPrinter(dotPrintTimer)
      }

      val l = Await.result(p.future, Duration.Inf)

      stopTimer(dotPrintTimer)
      println("")
      println(l)
    }
  }

  def timeOutPrinter(timer: Timer): Unit = {
    timer.schedule(new TimerTask {
      override def run(): Unit = {
        print(".")
      }
    }, 0, 50)
  }

}
