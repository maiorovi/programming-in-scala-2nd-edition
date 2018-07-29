package concurrency_in_scala.chapter4.ex

import scala.concurrent.Future
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

object Ex6 extends App {
  import scala.sys.process._

  def spawn(command: String): Future[Int] = {
    val p = Promise[Int]

    Future {
      blocking {
        p.complete(Try(command.!))
      }
    }

    p.future
  }

  spawn("ls").onComplete {
    case Success(st) => println(st)
    case Failure(ex) => println(ex.getMessage)
  }

  Thread.sleep(3000);
}
