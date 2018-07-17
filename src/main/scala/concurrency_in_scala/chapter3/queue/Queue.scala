package concurrency_in_scala.chapter3.queue

trait Queue[T] {
  def enqueue(x: T):Unit

  def dequeue(): T

  def isEmpty(): Boolean
}


object Queue {


  def blockingQueue[T]() : Queue[T] = ???

  import scala.collection.mutable;

  class BlockingQueue[T](val size: Int) extends Queue[T] {
    private val buffer = new mutable.ArrayBuffer[T](size)
    private var pointer: Int = 0

    override def enqueue(x: T): Unit = buffer.synchronized {
      while(buffer.size == size) buffer.wait()
      buffer += x
      pointer = 1
    }

    override def dequeue(): T = buffer.synchronized {
      while(buffer.isEmpty) buffer.wait()
      pointer = pointer - 1
      buffer(pointer)
    }

    override def isEmpty(): Boolean = buffer.synchronized {
      buffer.isEmpty
    }
  }

}
