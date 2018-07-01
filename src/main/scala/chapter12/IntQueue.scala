package chapter12

import scala.collection.mutable.ArrayBuffer

abstract class IntQueue {
  def get(): Int
  def put(x: Int)

}

class MyQueue extends BasicIntQueue with Doubling

trait Doubling extends IntQueue {
  abstract override  def put(x: Int) = {super.put(2*x)}
}

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]()

  override def get(): Int = buf.remove(0)

  override def put(x: Int): Unit = buf += x
}


object Demo123 {
  def main(args: Array[String]) : Unit = {
    val queue = new BasicIntQueue()
    queue.put(10)
    queue.put(20)

    println(queue.get())
    println(queue.get())

    val queue1 = new MyQueue()

    queue1.put(10)
    queue1.put(20)

    println(queue1.get())
    println(queue1.get())
  }
}