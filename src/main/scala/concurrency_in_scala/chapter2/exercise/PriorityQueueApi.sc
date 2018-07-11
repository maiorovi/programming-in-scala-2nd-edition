import scala.collection.mutable

//implicit object MyOrdering extends Ordering[Int] {
//  override def compare(x: Int, y: Int) = x.compareTo(y)
//}

val queue = new mutable.PriorityQueue[Int]()

queue.enqueue(4)
queue.enqueue(9)
queue.enqueue(10)

queue.dequeue()