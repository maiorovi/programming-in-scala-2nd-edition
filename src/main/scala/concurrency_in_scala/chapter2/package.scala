package concurrency_in_scala

package object chapter2 {

  def thread(body: => Unit): Thread = {
    val t = new Thread() {
      override def run(): Unit = body
    }
    t.start()
    t
  }

  def log(message: String): Unit = {
    println(s"${Thread.currentThread.getName}: $message")
  }
}
