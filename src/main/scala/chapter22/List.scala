package chapter22

import scala.collection.mutable.ListBuffer

trait List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]

  def map[U](f: T => U ):List[U] = if (isEmpty) Nil
  else f(head)::tail.map(f)

  def length: Int = if (isEmpty) 0 else 1 + tail.length

  def drop(n:Int): List[T] = if (isEmpty) Nil
  else if (n  <= 0) this
  else tail.drop(n-1)

  def ::[U >: T](node: U): List[U] = new ::(node, this)

  def :::[U >: T](prefix: List[U]): List[U] = if(prefix.isEmpty) this
  else prefix.head::prefix.tail:::this

}

object Nil extends List[Nothing] {
  override def isEmpty: Boolean = true

  override def head: Nothing = throw new NoSuchElementException

  override def tail: List[Nothing] = throw new NoSuchElementException

  override def toString: String = "Nil"
}


case class ::[T](head: T, tail: List[T]) extends List[T] {
  override def isEmpty: Boolean = false

  override def toString: String = s"$head::${tail.toString}"
}


object Demo extends App {
  val myList = 1::2::10::12::Nil
  val myList1 = 5::6::7::13::Nil

  println(myList)

  println(myList.map(_ * 2))

  println(s"length: ${myList.length}")

  println(myList:::myList1)

  val buf = new ListBuffer[Int]

  for (x <- 0 to 10) buf += x

  println(buf.toList)
}
