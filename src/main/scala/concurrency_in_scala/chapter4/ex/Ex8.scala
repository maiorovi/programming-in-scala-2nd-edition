package concurrency_in_scala.chapter4.ex

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Ex8 extends App {

  implicit class ExtendedPromise[T](self: Promise[T]) {
    def compose[S](f: S => T): Promise[S] = {
      val p = Promise[S]


      p.future.onComplete {
        case Success(s) => Future(self.trySuccess(f(s)))
        case Failure(e) => self.tryFailure(e)
      }

      p
    }
  }

  import concurrency_in_scala.chapter2._

  //test
  val pT = Promise[String]
  val pS: Promise[Int] = pT.compose(s => s"val = $s")

  Future {
    Thread.sleep(1000)
    pS.success(1)
    //    pS.failure(new Exception)
  }



  pT.future foreach {
    case s => log(s)
  }

  pT.future.failed foreach { case t => log(s"q failed with $t") }


  Thread.sleep(2000)

}
