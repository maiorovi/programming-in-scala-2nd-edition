package concurrency_in_scala.chapter2

import java.util.UUID

import concurrency_in_scala.chapter2.SynchronizedDeadlock.{a, b}

object SynchronizedNesting extends App {

  import scala.collection.mutable

  private val transfers = mutable.ArrayBuffer[String]()

  def logTransfer(name: String, n: Int) = transfers.synchronized {
    transfers += s"transfer to account '$name' = $n"
  }

  class Account(val name: String, var money: Int, val uid: Int)

  def add(account: Account, n: Int) = account.synchronized {
    account.money += n

    if (n > 10) logTransfer(account.name, n)
  }

  val jane = new Account("Jane", 100, 2)
  val john = new Account("John", 200, 1)

  val t1 = thread {
    add(jane, 5)
  }
  val t2 = thread {
    add(john, 50)
  }
  val t3 = thread {
    add(jane, 70)
  }

  t1.join();
  t2.join();
  t3.join()

  log(s"--- transfers --- \n $transfers")
}

object SynchronizedDeadlock extends App {

  import SynchronizedNesting.Account


  def send(a: Account, b: Account, n: Int) = {
    def adjust(): Unit = {
      a.money -= n
      b.money += n
    }


    if (a.uid > b.uid)
      a.synchronized {
        b.synchronized {
          adjust()
        }
      } else
      b.synchronized {
        a.synchronized {
          adjust()
        }
      }
  }

  val a = new Account("Jack", 1000, 3)
  val b = new Account("Jill", 2000, 4)

  val t1 = thread {
    for (i <- 0 until 1000) send(a, b, 1)
  }
  val t2 = thread {
    for (i <- 0 until 1000) send(b, a, 1)
  }

  t1.join()
  t2.join()

  log(s"a = ${a.money}, b = ${b.money}")
}
