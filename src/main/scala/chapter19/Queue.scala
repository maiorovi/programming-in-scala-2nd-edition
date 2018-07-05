package chapter19

trait Queue[T] {
  def head:T
  def tail: Queue[T]
  def enqueue(e:T): Queue[T]
}

class SlowAppendQueue[T](elems: List[T]) extends Queue[T] {
  override def head: T = elems.head

  override def tail: Queue[T] = new SlowAppendQueue[T](elems.tail)

  override def enqueue(e:T): Queue[T] = new SlowAppendQueue[T](elems++List(e))
}

class SlowHeadQueue[T](elems: List[T]) extends Queue[T] {
  override def head: T = elems.reverse.head

  override def tail: Queue[T] = new SlowHeadQueue(elems.reverse.tail);

  override def enqueue(e: T): Queue[T] = new SlowHeadQueue[T](e::elems)
}

class FastQueue[T] private (private val leading: List[T],
                            private val trailing: List[T]) extends Queue[T] {
  override def head: T = mirror.leading.head

  private def mirror():FastQueue[T] = if (leading.isEmpty) {
    new FastQueue[T](trailing.reverse, Nil)
  } else {
    this
  }

  override def tail: Queue[T] = {
    val q = mirror
    return new FastQueue[T](q.leading.tail, q.trailing)
  }


  override def enqueue(e: T): Queue[T] = new FastQueue[T](leading, e::trailing)
}

object FastQueue {
  def apply[T](xs: T*) = new FastQueue[T](xs.toList, Nil)
}

object DemoQueue {
  def main(args:Array[String]): Unit = {

  }

}
