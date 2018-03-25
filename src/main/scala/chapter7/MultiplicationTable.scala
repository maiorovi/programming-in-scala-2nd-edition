package chapter7

object MultiplicationTable {
  def main(args: Array[String]): Unit = {
    println(multiTable())
  }

  def makeRowSeq(row: Int) = {
    for (col <- 1 to 10) yield {
      val prod = (row * col).toString
      val padding = " " * (4 - prod.length)
      prod + padding
    }
  }

  def makeRow(row:Int) = makeRowSeq(row).mkString

  def multiTable() = {
    val tableSeq = for (row <- 1 to 10) yield makeRow(row)

    tableSeq.mkString("\n")
  }
}
