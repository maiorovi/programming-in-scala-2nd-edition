package chapter8

object RepeatedParametersDemo {
  def main(args:Array[String]):Unit = {
    speed(distance = 20, time = 10)
  }

  def echo(args: String*) =
    for(arg <- args) println(arg)

  def speed(distance: Float, time: Float): Float =
    distance / time
}
