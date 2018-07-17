package concurrency_in_scala.chapter3

import java.util.concurrent.atomic.AtomicReference

import scala.annotation.tailrec

object ConcurrentCollections {

  class AtomicBuffer[T] {

    private val buffer = new AtomicReference[List[T]](Nil)

    @tailrec final def +=(x: T): Unit = {
      val xs = buffer.get
      val newList = x::xs

      if(!buffer.compareAndSet(xs, newList)) this += x
    }
  }

  val buffer = new AtomicBuffer[Int]

  execute { for(x <- 0 until 10) buffer += x}
  execute { for(x <- 10 until 20) buffer += x}

}


object Reminder {
  class Amotic[T] {

    def compareAndSet(oldValue: T,  newValue: T): Boolean = {
      if(get != oldValue) false else {
        set(newValue)
        true
      }

    }

    def get():T = ???

    def set(x:T):Unit = ???
  }
}


