package concurrency_in_scala.chapter3

object LazyVals extends App {
  lazy val obj = new AnyRef
  lazy val non = s"made by ${Thread.currentThread.getName}"

  execute {
    log(s"EC sees obj = $obj")
    log(s"EC sees non = $non")
  }

  log(s"Main sees obj = $obj")
  log(s"Main sees non = $non")
  Thread.sleep(500)


}

object LazyValsAndBlocking extends App {
  import concurrency_in_scala.chapter2._

  lazy val x: Int = {
    val t =  thread { println(s"Initializing $x.")}
    t.join()
    1
  }

  x
}
