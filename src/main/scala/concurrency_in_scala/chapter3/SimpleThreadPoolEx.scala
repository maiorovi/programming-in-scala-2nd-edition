package concurrency_in_scala.chapter3

import java.util.concurrent.ForkJoinPool

object SimpleThreadPoolEx extends App {
  val executor = new ForkJoinPool()

  executor.execute( () => log("This is asynchronous task"))

  Thread.sleep(5000)

}
