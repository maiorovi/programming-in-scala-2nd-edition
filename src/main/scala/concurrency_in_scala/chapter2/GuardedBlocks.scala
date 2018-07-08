package concurrency_in_scala.chapter2

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
