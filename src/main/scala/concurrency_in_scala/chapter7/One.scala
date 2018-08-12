package concurrency_in_scala.chapter7

import java.util.concurrent.atomic.{AtomicInteger, AtomicReference}

import concurrency_in_scala.chapter7.One.{addUrl, getUrlArray}

import scala.annotation.tailrec
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object One {

  val urls = new AtomicReference[List[String]](Nil)
  val clen = new AtomicInteger(0)

  def addUrl(url: String): Unit = {
    @tailrec def append():Unit = {
      val oldUrls = urls.get()
      val newUrls = url :: oldUrls

      if (!urls.compareAndSet(oldUrls, newUrls)) append()
    }

    append()
    clen.addAndGet(url.length + 1)
  }

  def getUrlArray() : Array[Char] = {
    val array = new Array[Char](clen.get)
    Thread.sleep(20)
    urls.get().flatMap(u => u + "\n").zipWithIndex.foreach(p => array(p._2) = p._1)

    array
  }
}

object FixToOne {
  import scala.concurrent.stm._

  val urls = Ref[List[String]](Nil)
  val clen = Ref[Int](0)

  def addUrl(url: String): Unit = atomic { implicit  txn =>

    urls() = url :: urls()
    clen() = clen() + url.length  + 1
  }

  def getUrlArray() : Array[Char] = atomic { implicit txn =>
    val array = new Array[Char](clen())
    Thread.sleep(20)
    urls().flatMap(u => u + "\n").zipWithIndex.foreach(p => array(p._2) = p._1)

    array
  }
}

object AtomicHistoryBad extends App {

  Future {
    try { log(s"sending: ${One.getUrlArray().mkString}") }
    catch { case e: Exception => log(s"Houston... $e!") }
  }
  Future {
    One.addUrl("http://scala-lang.org")
    One.addUrl("https://github.com/scala/scala")
    Thread.sleep(5)
    One.addUrl("http://www.scala-lang.org/api")
    log("done browsing")
  }
  Thread.sleep(1000)
}

object AtomicHistoryGood extends App {

  Future {
    try { log(s"sending: ${FixToOne.getUrlArray().mkString}") }
    catch { case e: Exception => log(s"Houston... $e!") }
  }
  Future {
    FixToOne.addUrl("http://scala-lang.org")
    FixToOne.addUrl("https://github.com/scala/scala")
    Thread.sleep(25)
    FixToOne.addUrl("http://www.scala-lang.org/api")
    log("done browsing")
  }
  Thread.sleep(1000)
}
