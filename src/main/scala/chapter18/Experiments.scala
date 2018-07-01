package chapter18

class Experiments {
  private[this] var h = 12

  def hour: Int = return h

  def hour_=(x:Int) = {
    println("in setter")
    this.h = x
  }
}


object Demo31 {
  def main(args: Array[String]): Unit = {
    val exp = new Experiments();
    exp.hour = 13
    println(exp.hour)
  }
}

