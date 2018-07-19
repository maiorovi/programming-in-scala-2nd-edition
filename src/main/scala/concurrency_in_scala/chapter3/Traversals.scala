package concurrency_in_scala.chapter3

import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConverters._
import scala.collection.concurrent

object Traversals {


}

object CollectionsConcurrentMapBulk extends App {
  val names = new ConcurrentHashMap[String, Int]().asScala
  names("Johnny") = 0; names("Jane") = 0; names("Jack") = 0
  execute {
    for (n <- 0 until 10) names(s"John $n") = n }

  execute {
    for (n <- names) log(s"name: $n") }
  Thread.sleep(1000)
}

object CollectionsTrieMapBulk extends App {
  val names = new concurrent.TrieMap[String, Int]
  names("Janice") = 0; names("Jackie") = 0; names("Jill") = 0
  execute {for (n <- 10 until 100) names(s"John $n") = n}

  execute {
    log("snapshot time!")
    for (n <- names.map(_._1).toSeq.sorted) log(s"name: $n")
  }
  Thread.sleep(1000)
}


object PiggyBackContext {
  def execute(body: => Unit): Unit = {
    body
  }
}