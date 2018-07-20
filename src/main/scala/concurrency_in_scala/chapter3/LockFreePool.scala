package concurrency_in_scala.chapter3

import java.util.concurrent.atomic.AtomicReference

import scala.annotation.tailrec

object ExN {

  class LockFreePool[T] {
    private val paralellism = Runtime.getRuntime.availableProcessors * 32

    val pool = new Array[AtomicReference[(List[T], Long)]](paralellism)
    for (x <- pool.indices) pool(x) = new AtomicReference[(List[T], Long)]((Nil,0L))


    def add(x:T): Unit = {
      val ind = x.## % pool.length

      val oldValue = pool(ind).get

      val newList = x::oldValue._1
      val newLong = oldValue._2 + 1

      if (!pool(ind).compareAndSet(oldValue, (newList, newLong))) add(x)
    }

    def remove(): Option[T] = {
      val start = (Thread.currentThread.getId % pool.length).toInt

      @tailrec
      def scan(witness: Long): Option[T] = {
        var i = (start + 1) % pool.length
        var sum = 0L

        while(i != start) {
          val bucket = pool(i)

          @tailrec def retry(): Option[T] = {
            val oldPair = bucket.get()
            val newPair = (oldPair._1.tail, oldPair._2 - 1)

            if (!bucket.compareAndSet(oldPair, newPair)) retry()
            else oldPair._1 match {
              case Nil => sum = sum + oldPair._2; None
              case head::tail => Some(head)
            }
          }

          retry() match {
            case Some(v) => return Some(v)
            case None =>
          }

          i = (i + 1) % pool.length
        }

        if (witness != sum) scan(sum)
        else None
      }

      scan(-1L)
    }

    def foreach(f: T => T ): Unit = {
      pool.foreach(processBucket)

      def processBucket(bucket: AtomicReference[(List[T], Long)]): Unit = {
        bucket.get match {
          case (Nil, s) =>
          case old@(list, s) => if (!bucket.compareAndSet(old, (list.map(f), s))) processBucket(bucket)
        }
      }
    }


  }

}
