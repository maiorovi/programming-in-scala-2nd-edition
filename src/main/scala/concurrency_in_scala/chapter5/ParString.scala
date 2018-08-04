package concurrency_in_scala.chapter5

import scala.collection.immutable
import scala.collection.immutable.WrappedString
import scala.collection.parallel.{ParSeq, SeqSplitter}
import scala.collection.parallel.immutable._

//class ParString(val str: String) extends ParSeq[Char] {
//  override def apply(i: Int): Char = str.charAt(i)
//
//  override protected def splitter: SeqSplitter[Char] = ???
//
//  override def seq: Seq[Char] = new WrappedString(str)
//
//  override def length: Int = str.length
//}
//
//
//class ParStringSplitter(val s: String, var i: Int, val limit: Int) extends SeqSplitter[Char] {
//  override def dup: SeqSplitter[Char] = new ParStringSplitter(s, i, limit)
//
//  override def split: Seq[SeqSplitter[Char]] = {
//    val rem = remaining
//    if (rem >= 2) psplit(rem / 2, rem - rem / 2)
//    else Seq(this)
//  }
//
//  override def psplit(sizes: Int*): Seq[SeqSplitter[Char]] = ???
//
//  override def remaining: Int = limit - i
//
//  override def hasNext: Boolean = i < limit
//
//  override def next(): Char = {
//    var r = s.charAt(i)
//    i += 1
//    r
//  }
//}