package chapter10

import chapter10.Element.elem

abstract class Element {
  def contents: Array[String]

  def above(that: Element): Element = {
    val this1 = this widen that.width
    val that1 = that widen this.width

    elem(this1.contents ++ that1.contents)
  }
  def height: Int = contents.length
  def width: Int = if (height == 0) 0 else contents(0).length

  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height

    elem(
      for(
        (line1, line2) <- this1.contents zip that1.contents
      ) yield line1 + line2
    )
  }

  override def toString: String = contents mkString "\n"

  def widen(w: Int): Element =
    if (w <= width) {
      this
    } else {
        val left = elem(' ', (w - width) / 2, height)
        val right = elem(' ', w - width - left.width, height)
      left beside this beside right
    }

  def heighten(h: Int): Element =
    if (h <= height) this
    else {
      val top = elem(' ', width, (h - height) / 2)
      val bot = elem(' ', width, h - height - top.height)
      top above this above bot
    }

}



object Element {

  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)

  def elem(contents: String): Element =
    new LineElement(contents)


  def elem(ch: Char, width: Int, height: Int): Element =
    new UniformElement(ch, width, height)

  class ArrayElement(val contents: Array[String]) extends Element
  class LineElement(s: String) extends Element {
    override val contents: Array[String] = Array(s)
    override val width: Int = s.length
    override val height: Int = 1
  }
  class UniformElement(
                        ch: Char,
                        override val width: Int,
                        override val height: Int) extends Element {
    private val line = ch.toString * width
    override def contents = Array.fill(height)(line)

  }
}