package concurrency_in_scala.chapter4.ex

import scala.collection.concurrent.TrieMap
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}

object Ex7 extends App {

  import scala.collection.concurrent.Map

  class IMap[K, V] {

    private val container: Map[K, Promise[V]] = new TrieMap[K, Promise[V]]()

    def update(k: K, v: V): Unit = container.putIfAbsent(k, createPromise(v)) match {
      case None =>
      case Some(old) => {
        try {
          old.success(v)
        } catch {
          case e: IllegalStateException => throw new Exception("A specific key can be assigned only once")
          case e => throw e
        }
      }
    }


//      if (container.contains(k) && container(k).isCompleted) throw new Exception else {
//      if (container.contains(k)) container(k).complete(Try(v)) else {
//        val p = Promise[V]
//        p.complete(Try(v))
//        container.put(k, p)
//      }
//    }

    def apply(k: K): Future[V] = container.get(k) match {
      case Some(value) => value.future
      case None => createEmptyPromise(k).future
    }
//      if (container.contains(k)) container(k).future else {
//      val p = Promise[V]
//      container.put(k, p)
//
//      p.future
//    }

    private def createEmptyPromise(k: K): Promise[V] = {
      val p = Promise[V]

      container.putIfAbsent(k, p) match {
        case Some(old) => old
        case None => p
      }
    }

    private def createPromise(v: V): Promise[V] = {
      val  p = Promise[V]
      p.success(v)

      p
    }

  }

  import concurrency_in_scala.chapter2._
  val m = new IMap[Int, String]()

  (1 to 100).map(i => thread {
    m.update(1, Thread.currentThread().getName)
  })

  (1 to 100).map(i => thread {
    val l = Await.result(m(1), Duration.Inf)
    log(l)
  })


}
