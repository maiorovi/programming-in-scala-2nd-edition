package concurrency_in_scala.chapter2

object ThreadsMain extends App {

//  val t: Thread = Thread.currentThread()
//  val name= t.getName
//
//  println(s"I am the thread $name")

  val t = thread {
    println("New thread running!")
  }

  t.join()
  println("Thread finished")
}

object ThreadSleep extends App {
  val t = thread {
    Thread.sleep(1000)
    log("New thread running")
    Thread.sleep(1000)
    log("still running")
    Thread.sleep(1000)
    log("completed")
  }

  t.join()
  log("New thread joined")
}


object ThreadStateAccessReordering extends App {
  for (i <- 0 until 100000) {
    var a = false
    var b = false
    var x = -1
    var y = -1

    val t1 = thread {
      this.synchronized {
        a = true
        y = if (b) 0 else 1
      }
    }

    val t2 = thread {
      this.synchronized {
        b = true
        x = if (a) 0 else 1
      }
    }

    t1.join()
    t2.join()

    assert (!(x == 1 && y == 1), s"x = $x, y = $y")
  }
}