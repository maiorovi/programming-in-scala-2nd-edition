package concurrency_in_scala.chapter4

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import concurrency_in_scala.chapter2.log
import scala.concurrent.duration._
import scala.io.Source
import scala.util.{Failure, Success, Try}

object Promises extends App {
  val q = Promise[String]
  val p = Promise[String]

  p.future foreach { case x => log(s"p succeeded with '$x'")}

  Thread.sleep(1000)

  p success "assigned"

  q failure new Exception("not kept")

  q.future.failed foreach { case t => log(s"q failed with $t")}
  Thread.sleep(1000)


  val z = Promise[String]

  z.complete {
    if (true) {
      Success("fds")
    } else {
      Failure(new Exception("something went wrong"))
    }
  }

  Thread.sleep(1000)

}

object PromisesCustomAsync extends App {

  def myFuture[T](body: => T):Future[T] = {
    val p = Promise[T]
    global.execute(() => {
      Try(body) match {
        case Success(v) => p.success(v)
        case Failure(ex) => p.failure(ex)
      }
    })

      p.future
    }

  val f = myFuture { "naa" + "na" * 8 + " Katamari Damacy!" }
  f foreach { case text => log(text) }
  Thread.sleep(1000)
}

object FuturesAndBlocking extends App {

  val urlSpecSizeFuture = Future {
    val specUrl = "http://www.w3.org/Addressing/URL/url-spec.txt"
    Source.fromURL(specUrl).size
  }

  val urlSpecSize = Await.result(urlSpecSizeFuture, 10.seconds)
  log(s"url spec contains $urlSpecSize characters")
}

object AsyncFramework extends App {
  import scala.async.Async.{async, await}

  def delay(n: Int): Future[Unit] = async {
//    blocking {
//
//    }
  }

  async {
    log("T-minus 1 second")
    await { delay(1) }
    log("done!")
  }


}