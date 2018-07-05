trait AbstractTime {
  var hour: Int
  var minute: Int
  require(hour != 0)
}

class ConcreteTime extends AbstractTime {
  override def hour = 10
  override def hour_=(x: Int) = println("hello")

  override def minute = 10
  override def minute_=(x: Int) = println("hello")
}

new {
  override var hour: Int = 10
  override var minute: Int = 20
} with AbstractTime

//pre initialized fields in class definition

trait RationalTrait {
  val numerArg: Int
  val denomArg: Int
  require(denomArg != 0)
  private val g = gcd(numerArg, denomArg)
  val numer = numerArg / g
  val denom = denomArg / g
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
  override def toString = numer + "/" + denom
}

//pre initialized fields simulates behaviour of constructor parameters
// in order to solve problem of overriding abstract fields because
// before fields overriding is invoked will be called parent class constructor
case class RationalClass(n: Int, d: Int) extends {
  val numerArg = n
  val denomArg = d + n*2
} with RationalTrait {
  def + (that: RationalClass) = new RationalClass(
    numer*that.denom + that.numer * denom,
    denom * that.denom
  )
}

RationalClass(3, 5)