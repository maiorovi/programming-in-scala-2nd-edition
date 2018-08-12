package concurrency_in_scala

package object chapter7 {
  def log(msg: String): Unit = {
    println(s"${Thread.currentThread().getName}: ${msg}")
  }
}
