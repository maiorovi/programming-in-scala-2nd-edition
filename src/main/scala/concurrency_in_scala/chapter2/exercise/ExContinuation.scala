package concurrency_in_scala.chapter2.exercise

import concurrency_in_scala.chapter2.SynchronizedNesting.Account

import scala.collection.mutable

object Ex7 extends App {

  class Account(val name: String, var money: Int, val uid: Int)

  def sendAll(accounts: Set[Account], target: Account): Unit = {
    for (account <- accounts) {
      def adjust(): Unit = {
        target.money += account.money
        account.money = 0
      }

      if (account.uid > target.uid) {
        account.synchronized {
          target.synchronized {
            adjust()
          }
        }
      } else {
        target.synchronized {
          account.synchronized {
            adjust()
          }
        }
      }

    }
  }

}


object Ex8 extends App {

  class PriorityAwareTask(val priority: Int)(taskBody: => Unit) {
    val task = () => taskBody
  }


  object PriorityAwareTask {

    def intPriorityOredering(task: PriorityAwareTask): Int = task.priority
  }

  class PriorityTaskPool() {

    private val priorityQueue: mutable.PriorityQueue[PriorityAwareTask] =
      new mutable.PriorityQueue[PriorityAwareTask]()(Ordering.by(PriorityAwareTask.intPriorityOredering))

    def submit(priorityAwareTask: PriorityAwareTask): Unit = this.synchronized {
      priorityQueue.enqueue(priorityAwareTask)
      notify()
    }

    def poll(): PriorityAwareTask = this.synchronized {
      if (this.isEmpty()) this.wait()

      priorityQueue.dequeue()
    }

    def isEmpty(): Boolean = this.synchronized {
      priorityQueue.isEmpty
    }

    private val worker = new Thread() {
      override def run(): Unit = while(true) {
        val task = tasks.poll()
        task.task()
      }
    }

    worker.start()
  }


  val tasks = new PriorityTaskPool()

  def asynchronous(priority: Int)(task: => Unit): Unit = {
    tasks.submit(new PriorityAwareTask(priority)(task))
  }

  asynchronous(10) {
    println("Hello with priority 10")
  }

  asynchronous(12) {
    println("World with priority 12")
  }

//  worker.join()

}

object Ex9 extends App {

  import concurrency_in_scala.log

  class PriorityAwareTask(val priority: Int)(taskBody: => Unit) {
    val task = () => taskBody
  }


  object PriorityAwareTask {

    def intPriorityOredering(task: PriorityAwareTask): Int = task.priority
  }

  class PriorityTaskPool(val size: Int) {

    private val priorityQueue: mutable.PriorityQueue[PriorityAwareTask] =
      new mutable.PriorityQueue[PriorityAwareTask]()(Ordering.by(PriorityAwareTask.intPriorityOredering))

    def submit(priorityAwareTask: PriorityAwareTask): Unit = this.synchronized {
      priorityQueue.enqueue(priorityAwareTask)
      notify()
    }

    def poll(): PriorityAwareTask = this.synchronized {
      if (this.isEmpty()) this.wait()

      priorityQueue.dequeue()
    }

    def isEmpty(): Boolean = this.synchronized {
      priorityQueue.isEmpty
    }

    val workers = for(i <- 0 until size) yield new Thread() {
      override def run(): Unit = while(true) {
        val task = poll()
        task.task()
      }
    }

    workers.foreach(_.start)
  }


  val tasks = new PriorityTaskPool(10)

  def asynchronous(priority: Int)(task: => Unit): Unit = {
    tasks.submit(new PriorityAwareTask(priority)(task))
  }

  asynchronous(10) {
    log("Hello with priority 10")
  }

  asynchronous(12) {
    log("World with priority 12")
  }

  for(i <- 0 until 100) {
    val a = (Math.random*1000).toInt
    asynchronous(a)({log(s"<- $a")})
  }

  //  worker.join()

}
