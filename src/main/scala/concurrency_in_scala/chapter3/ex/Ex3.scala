package concurrency_in_scala.chapter3.ex

import java.util.concurrent.atomic.AtomicReference

object Ex3 {

  class ConcurrentSortedList[T](implicit  val ord: Ordering[T]) {
    private val list = new AtomicReference[List[T]](Nil)

    def add(x: T): Unit = {
      val oldList = list.get()
      val newList = x::oldList

      if(!list.compareAndSet(oldList, newList)) add(x)
    }

    def iterator: Iterator[T] = list.get().sorted.iterator

  }

}

object Ex5 {
  class LazyCell[T](initialization: => T) {

    @volatile var isInitialized = false

    var _lazyBody:T = _

    def lazyBody: T = if (isInitialized) _lazyBody else this.synchronized {
      if(!isInitialized) {
        _lazyBody = initialization
        isInitialized = true
      }

      _lazyBody
    }

    def apply(): T = lazyBody
  }
}

object Ex6 {
  class PureLazyCell[T](initialization: => T) {

    var l = new AtomicReference[Option[T]](None)

    def apply(): T = l.get match {
      case Some(v) => v
      case None => {
        if(!l.compareAndSet(None, Some(initialization))) apply()
        else l.get().get
      }
    }

  }
}