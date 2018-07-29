package concurrency_in_scala.chapter4.ex

import scala.concurrent.Future
import scala.concurrent.{Await, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Ex3  extends App {
  implicit class ExtendedFuture[T](f: Future[T]) {
    def exists(p: T => Boolean): Future[Boolean] = f.map(p)
  }

  val f = Future {
    println("Starting processing")
    Thread.sleep(1500)
    "Result"
  }

  f.exists(s => s.length == 7)
    .onComplete {
      case Success(t) => println(t)
      case Failure(ex) => println(ex.getMessage)
    }

  Thread.sleep(5000)

}

object Ex4 extends App {
  implicit class ExtendedFuture[T](f: Future[T]) {
    def exists(p: T => Boolean): Future[Boolean] = {
      val promise = Promise[Boolean]
      f.foreach(r => promise.success(p(r)))
      f.failed foreach (_ => promise.success(false))
      promise.future
    }

  }
}


object Ex5 extends App {
  import scala.async.Async.{async, await}

  implicit class ExtendedFuture[T](self: Future[T]) {
    def exist(p: T => Boolean): Future[Boolean] = async {
        p(await(self))
    }.recover {case _ => false }
  }
}
