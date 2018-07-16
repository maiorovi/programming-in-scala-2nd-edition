package concurrency_in_scala.chapter3


import java.util.concurrent.atomic.{AtomicLong, AtomicReference}

import scala.annotation.tailrec

object AtomicPrimitives {

  class AtomicUidCas {

    private val uid:AtomicLong = new AtomicLong(0)

    @tailrec
    final def getUniqueId(): Long = {
      val ov = uid.get()
      val nv = ov + 1

      if (uid.compareAndSet(ov, nv)) nv
      else getUniqueId()
    }


  }
}


object AtomicLock extends App{
  import java.util.concurrent.atomic.AtomicBoolean

  private val lock = new AtomicBoolean(false)

  private def mySynchronized(body: => Unit): Unit = {
    while(!lock.compareAndSet(false, true)) {}
    try body finally lock.set(false)
  }

  var count = 0

  for (i <- 0 until 10) execute {
    mySynchronized {
      count += 1
    }
  }

  Thread.sleep(1000)
  log(s"Count is: $count")

}

object AtomicStack {
  trait Stack
  class Node(hd: Int, tail: Stack) extends Stack
  object Bottom extends Stack

  val stack = new AtomicReference[Stack](Bottom)

  @tailrec def push(x: Int): Unit = {
    val oldTop = stack.get()
    val newTop = new Node(x, oldTop)
    if (!stack.compareAndSet(oldTop, newTop)) push(x)
  }


//  def pop():Option[Int] = stack.get() {
//    case Bottom => None
//  }
}