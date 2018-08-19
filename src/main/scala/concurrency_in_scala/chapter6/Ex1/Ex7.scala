package concurrency_in_scala.chapter6.Ex1

import rx.lang.scala.{Observable, Subject}

import scala.collection.mutable

object Ex7 extends App {

  class RPriorityQueue[T](implicit val ord: Ordering[T]) {
    private[this] val pq = new mutable.PriorityQueue[T]()(ord.reverse)
    private[this] val obs = Subject[T]

    def add(x:T): Unit = pq += x

    def pop(): T = {
      val item = pq.dequeue

      obs.onNext(item)

      item
    }

    def popped: Observable[T] = obs
  }

  import scala.collection.mutable.ListBuffer

  val rqueue = new RPriorityQueue[Int]()
  rqueue.add(3)
  rqueue.add(1)
  rqueue.add(2)

  val o = rqueue.popped
  val buf = ListBuffer.empty[Int]
  o.subscribe(buf += _)

  assert(rqueue.pop() == 1)
  assert(rqueue.pop() == 2)
  assert(rqueue.pop() == 3)
  assert(buf == ListBuffer(1, 2, 3))

}
