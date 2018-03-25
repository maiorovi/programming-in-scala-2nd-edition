package chapter6

import scala.annotation.tailrec

class Rational(n: Int, d: Int) {
  require(d != 0)

  private val g: Int = gcd(n.abs, d.abs)

  val numer: Int = n / g
  val denom: Int = d / g

  @tailrec
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  def this(n: Int) = this(n, 1) //auxiliary constructor

  def this(r: Rational) = this(r.numer, r.denom)

  def add(that: Rational): Rational = new Rational( numer * that.denom + that.numer * denom, denom * that.denom)

  def +(that:Rational): Rational = add(that)

  def +(that: Int) : Rational = this + new Rational(that)


  def *(that:Rational):Rational = new Rational(numer * that.numer, denom * that.denom)

  def *(that: Int) : Rational = this * new Rational(that)

  def /(that:Rational):Rational = new Rational(numer * that.denom, denom * that.numer)

  def /(that:Int):Rational = this / new Rational(that)

  def lessThan(that: Rational) = this.numer * that.denom < that.numer * this.denom

  def max(that: Rational) = if (this.lessThan(that)) that else this

  override def toString: String = n + "/" + d
}
