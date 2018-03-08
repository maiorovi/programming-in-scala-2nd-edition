package cake

object MyApplication {

  def main(args: Array[String]):Unit = {
    ComponentRegistry.sayHelloService.sayHello
  }

}
