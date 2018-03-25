package chapter6

object RationalDemo {

  def main(args: Array[String]):Unit = {
    val rational1 = new Rational(10, 12)
    val rational2 = new Rational(1, 12)
    val rational3 = new Rational(3)

    println(rational1.denom)

    println(rational1 add rational2)

    println( s"${rational1} + ${rational1} = ${rational1 + rational1}" )
    println( s"${rational2} * ${rational3} = ${rational2 * rational3}" )
    println( s"${rational2} / ${rational3} = ${rational2 / rational3}")
  }


}
