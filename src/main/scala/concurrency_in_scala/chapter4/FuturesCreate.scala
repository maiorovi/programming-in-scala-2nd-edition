package concurrency_in_scala.chapter4

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

object FuturesCreate extends App {
  import concurrency_in_scala.chapter2._

  Future { log("the future is here")}
  log("the future is coming")
  Thread.sleep(1000)
}

object FuturesDataType extends App {
  import concurrency_in_scala.chapter2._

  val buildFile: Future[String] = Future {
    val f = Source.fromFile("/app/Projects/Programming-In-Scala/src/main/scala/concurrency_in_scala/Tricks.sc")
    try f.getLines.mkString("\n") finally f.close()
  }
  log(s"started reading the build file asynchronously")
  log(s"status: ${buildFile.isCompleted}")
  Thread.sleep(250)
  log(s"status: ${buildFile.isCompleted}")
  log(s"value: ${buildFile.value}")
}

object FuturesCallbacks extends App {
  def getUrlSpec(): Future[List[String]] = Future {
    val url = "https://en.wikipedia.org/wiki/JSON"
    val f = Source.fromURL(url)
    try f.getLines.toList finally f.close()
//    List("1","2","3")
  }

  val urlSpec: Future[List[String]] = getUrlSpec()
//  urlSpec.foreach(l => println(l.mkString("\n")))

    Thread.sleep(5000)
//    println(urlSpec.value)
}
