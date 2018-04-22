package chapter12

import scala.collection.mutable.ArrayBuffer

abstract class IntQueue {
  def get(): Int
  def put(x: Int)

}


class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]()

  override def get(): Int = buf.remove(0)

  override def put(x: Int): Unit = buf += x
}


object Demo {
  def main(args: Array[String]) : Unit = {

  }
}