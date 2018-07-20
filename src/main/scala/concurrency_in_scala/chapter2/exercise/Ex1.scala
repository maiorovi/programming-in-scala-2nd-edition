package concurrency_in_scala.chapter2.exercise

import concurrency_in_scala.chapter2.exercise.Common.thread
import concurrency_in_scala.chapter2.exercise.Ex5.SyncContainer
import concurrency_in_scala.log

object Ex1 extends App {
  import Common.thread

  def parallel[A,B](a: => A, b: => B): (A,B) = {
    var r1:A = null.asInstanceOf[A]
    var r2:B = null.asInstanceOf[B]

    val aTh = thread { r1 = a }
    val bTh = thread { r2 = b }

    aTh.join()
    bTh.join()

    (r1, r2)
  }



  println(parallel(
    {
        "this is a"

    }, {
      "this is b"
    }))

}


object Ex2 extends App {
  import Common.thread

  def periodically(duration: Long)(b: => Unit): Unit = {
     thread {
      while(true) {
        val startTime = System.currentTimeMillis()
        b

        val endTime = System.currentTimeMillis()
        val tookTime = endTime-startTime

        if (duration > tookTime) {
          Thread.sleep(duration - tookTime)
        }
      }
    }
  }

  periodically(3000) {
    println("hello world")
  }

}

object Ex3AndEx4 extends App{
  class SyncVar[T] {

    @volatile private var empty: Boolean = true

    private var t = null.asInstanceOf[T]


    def get(): T = this.synchronized {
      if (empty) throw new RuntimeException
      else {
        val tmp = t
        t = null.asInstanceOf[T]
        empty = true
        tmp
      }
    }


    def put(x:T):Unit = this.synchronized {
      if (!empty) throw new RuntimeException
      else {
        empty = false
        t = x
      }
    }

    def isEmpty:Boolean = {
      while(!empty) {}
      true
    }

    def nonEmpty:Boolean = {
      while (empty) {}
      true
    }
  }

  import Common.thread
  import concurrency_in_scala.log

  val container = new SyncVar[Int]()

  val producer = thread {
    for(x <- 0 until 15) {
      if(container.isEmpty) {
        container.put(x)
      }
    }
  }

  val consumer = thread {
    while(container.nonEmpty) {
      log(container.get().toString)
    }
  }
}

object Ex5 extends App {

  trait SyncContainer[T] {
    def getWait():T
    def putWait(x:T)
  }

  class SyncVar[T] extends SyncContainer[T] {

    @volatile private var empty: Boolean = true

    private var t = null.asInstanceOf[T]


    def get(): T = this.synchronized {
      if (empty) throw new RuntimeException
      else {
        val tmp = t
        t = null.asInstanceOf[T]
        empty = true
        tmp
      }
    }


    def put(x:T):Unit = this.synchronized {
      if (!empty) throw new RuntimeException
      else {
        empty = false
        t = x
      }
    }

    def isEmpty:Boolean = this.synchronized {
      empty
    }

    def nonEmpty:Boolean = this.synchronized{
      !empty
    }

    override def getWait(): T = this.synchronized {
      while(isEmpty) this.wait()
      this.notify()
      empty = true
      t
    }

    override def putWait(x:T):Unit = this.synchronized {
      while(nonEmpty) this.wait()
      t = x
      empty = false
      this.notify()
    }
  }


 import Common.thread
 import concurrency_in_scala.log

  val container = new SyncVar[Int]()

  val producer = thread {
    for(x <- 0 until 15) {
        container.putWait(x)
    }
  }

  val consumer = thread {
    while(true) {
      log(container.getWait().toString)
    }
  }

}

object Ex6 extends App {

  import scala.collection.mutable

  class SyncQueue[T](val n: Int) extends SyncContainer[T] {
    private val queue: mutable.Queue[T] = mutable.Queue[T]()

    override def getWait(): T = this.synchronized {
      while(queue.isEmpty) this.wait()
      this.notify()
      queue.dequeue()
    }

    override def putWait(x: T): Unit = this.synchronized {
      while (queue.size == n) this.wait()

      queue.enqueue(x)
      this.notify()
    }
  }

  val queue = new SyncQueue[Int](15)

  val producer = thread {
    for(x <- 0 until 16) {
      queue.putWait(x)
    }
  }

  val consumer = thread {
    while(true) {
      log(queue.getWait().toString)
    }
  }
}



object Common {

  def thread[T](body: => T):Thread = {

    val t = new Thread {
      override def run(): Unit =  body
    }
    t.start()

    t
  }
}