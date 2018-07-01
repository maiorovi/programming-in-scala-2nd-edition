package chapter13

class OneProcessor {

  def giveMeOneToProcess(one: One): Unit = {
    //you can apply imports on objects! cool!
    import one._
//    println(s"$first $second")
    println(concat(first, second))
  }
}
