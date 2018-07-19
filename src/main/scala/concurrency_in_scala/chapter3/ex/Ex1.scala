package concurrency_in_scala.chapter3.ex

import java.util.concurrent.atomic.AtomicReference

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

object Ex1 extends App {

  import scala.concurrent._
  import concurrency_in_scala.chapter2.log

  class PiggybackContext extends ExecutionContext {

    override def execute(runnable: Runnable): Unit = Try(runnable.run()) match {
      case Success(r) => log("result: OK")
      case Failure(q) => reportFailure(q)
    }

    override def reportFailure(cause: Throwable): Unit = {
      log(s"error: ${cause.getMessage}")
    }
  }

  val e = new PiggybackContext

  e.execute(new Runnable {
    override def run(): Unit = {
      log("run (exception)")
      throw new Exception("test exception")
    }
  })

  e.execute(new Runnable {
    override def run(): Unit = {
      log("run")
    }
  })
}

object Ex2 extends App {
  class TreiberStack[T] {
    private val list = new AtomicReference[List[T]](Nil)

    @tailrec final def push(x: T): Unit = {
      val oldList = list.get()
      val newList = x::oldList

      if(!list.compareAndSet(oldList, newList)) push(x)
    }

    @tailrec final def pop(): T = {
      val oldList = list.get()
      val value = oldList.head
      if(!list.compareAndSet(oldList, oldList.tail)) pop()
      else value
    }
  }

  import concurrency_in_scala.chapter2._

  val s = new TreiberStack[Int]

  val t1 = thread {
    for (i <- 1 to 10) {
      s.push(i)
      Thread.sleep(1)
    }
  }

  val t2 = thread  {
    for (i <- 1 to 10) {
      s.push(i*10)
      Thread.sleep(1)
    }
  }

  t1.join()
  t2.join()

  for (i <- 1 to 20)
    log(s"s[$i] = ${s.pop()}")

}


