package concurrency_in_scala.chapter4.ex

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

object Ex2 extends App {
  class IVar[T] {
    private var value: Promise[T] = _

    def apply(): T = if (!value.isCompleted) throw new Exception else Await.result(value.future, Duration.Inf)

    def :=(x:T): Unit = if (!value.trySuccess(x)) throw new Exception else value.success(x)
  }

}
