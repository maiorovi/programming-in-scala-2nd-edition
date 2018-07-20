package concurrency_in_scala.chapter3

import java.util.concurrent.atomic.AtomicReference

object Ex3_new {

  class LazyCell[T](initializing: => T) {

    private var x: Option[T] = None

    def apply(): T = x match {
      case Some(v) => v
      case None => this.synchronized {
        x match {
          case None => x = Some(initializing); x.get
          case Some(v) => v
        }
      }
    }
  }

  class PureLazyCell[T](initializing: => T) {
    private var x = new AtomicReference[Option[T]]()

    def apply(): T = x.get() match {
      case Some(z) => z
      case old@None => if (!x.compareAndSet(old, Some(initializing))) apply() else x.get().get
    }
  }

}
