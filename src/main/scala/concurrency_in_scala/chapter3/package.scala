package concurrency_in_scala

import scala.concurrent.ExecutionContext

package object chapter3 {

  def execute(body: => Unit) = ExecutionContext.global.execute(() => body)

  def log(msg: String): Unit = {
    println(s"${Thread.currentThread.getName} : ${msg}")
  }
}
