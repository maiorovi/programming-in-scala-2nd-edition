package concurrency_in_scala.chapter6.Ex1

import java.util
import java.util.Timer

import rx.lang.scala.{Observable, Observer, Subscription}
import concurrency_in_scala.chapter6.log

import scala.annotation.tailrec
import scala.collection.mutable

object Ex1 extends App {
//  Observable.
  import scala.concurrent.duration._
  import concurrency_in_scala.chapter2.thread

  val rootThreadGroup = getRootThread(Thread.currentThread.getThreadGroup)
  var existThreads = getCurrentThreads().toSet

  @tailrec
  def getRootThread(t: ThreadGroup): ThreadGroup = {
    val parent = t.getParent
    if (parent == null) t else getRootThread(parent)
  }

  def getCurrentThreads() = {
    val threads = new Array[Thread](rootThreadGroup.activeCount())
    rootThreadGroup.enumerate(threads, true)

    threads.filter(_ != null)
  }

  def getNewThreads() = {
    val currentThreads = getCurrentThreads()
    val newThreads = currentThreads.filter(!existThreads.contains(_))

    existThreads = newThreads.toSet

    newThreads
  }

  def createObservableNewThreads: Observable[Thread] = {
    Observable.apply[Thread] { obs =>
      getNewThreads().foreach(obs.onNext)
    }
  }


  val obs = Observable.interval(1.seconds)
    .flatMap(_ => createObservableNewThreads)

  obs.subscribe(t => log(s"created new thread ${t}"))


    Thread.sleep(1000)
  thread {
    Thread.sleep(5000)
    println("aba")
  }

  thread {
    Thread.sleep(500)
    println("aba")
  }

  thread {
    println("aba")
  }


}

object Ex2 extends App {
  import scala.concurrent.duration._

  val obs = Observable.interval(1.seconds)
    .filter(t => (t % 5 == 0 && t % 30 != 0) || t % 12 == 0)

  obs.subscribe(p => log(p))

  Thread.sleep(60000)
}
