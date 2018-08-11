package concurrency_in_scala.chapter6.Ex1

import rx.lang.scala.{Observable, Subject}

object Ex6 extends App {

  class RMap[K, V] {

    import scala.collection.mutable

    val map = mutable.Map.empty[K, Subject[V]]

    def update(k: K, v: V): Unit = {
      if (map.contains(k)) {
        map(k).onNext(v)
      } else {
        val subject = Subject[V]()
        map += (k -> subject)
        subject.onNext(v)
      }
    }

    def apply(k: K): Observable[V] = if (map.contains(k)) {
      map(k)
    } else {
      val subject = Subject[V]()
      map.put(k, subject)
      subject
    }
  }


  val capitals = new RMap[String, String]

  val p = capitals("England")

  p.subscribe(s => println(s))

  capitals.update("England", "London")
  Thread.sleep(2000)
  capitals.update("England", "Manchester")
  Thread.sleep(5000)

}
