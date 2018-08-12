package concurrency_in_scala.chapter7

import scala.concurrent.stm._

object Two {

  case class Node(elem: Int, next: Ref[Node]) {
    def append(n: Node): Node = atomic { implicit txn =>
      val c = n.next()
      this.next() = n
      n.next() = c
      this
    }

    def nextNode: Node = next.single()

    def appendIfEnd(n: Node) = next.single.transform {
      oldNext => if (oldNext == null) n else oldNext
    }
  }

  def nodeToString(n: Node): String = atomic { implicit  txn =>
    val b= new StringBuilder
    var curr = n
    while(curr != null ) {
      b.append(curr.elem)
      curr = n.next()
    }

    b.toString()
  }
}
