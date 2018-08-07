package concurrency_in_scala

package object chapter6 {
  def log[T](body: => T): Unit = {
    println(s"${Thread.currentThread.getName} :  $body")
  }
}
