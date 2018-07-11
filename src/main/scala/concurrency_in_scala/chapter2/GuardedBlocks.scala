package concurrency_in_scala.chapter2

import scala.annotation.tailrec

object GuardedBlocks extends App {
  import scala.collection.mutable

  private val tasks = mutable.Queue[() => Unit]()

  val worker = new Thread {

    def pool(): Option[() => Unit] = tasks.synchronized {
      if (tasks.nonEmpty) Some(tasks.dequeue()) else None
    }

    override def run(): Unit = while (true) pool() match {
      case Some(task) => task()
      case None =>
    }
  }

  worker.setName("Worker")
  worker.setDaemon(true)
  worker.start()

  def asynchronous(body: => Unit): Unit = tasks.synchronized{
    tasks.enqueue(() => body)
  }


  asynchronous {
    log("Hello")
  }

  asynchronous {
    log(" world!")
  }

  Thread.sleep(5000)
}


object SynchronizedPool extends App {
  import scala.collection.mutable

  var terminated = false

  val tasks = new mutable.Queue[() => Unit]()

  val worker = new Thread {

    private def poll(): Option[() => Unit] = tasks.synchronized {
      while (tasks.isEmpty && !terminated) tasks.wait()

      if (!terminated) Some(tasks.dequeue()) else None
    }

    @tailrec override def run(): Unit = poll() match {
      case Some(x) => x(); run()
      case None =>
    }


    def shutdown() = tasks.synchronized {
      terminated = true
      tasks.notify()
    }
  }

  worker.start()

  def asynchronous(body: => Unit):Unit = tasks.synchronized {
    tasks.enqueue(() => body)
    tasks.notify()
  }

  asynchronous {
    log("hello")
  }

  asynchronous {
    log("my world")
  }

//  Thread.sleep(5000)
}
