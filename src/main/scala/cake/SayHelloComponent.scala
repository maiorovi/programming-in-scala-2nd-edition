package cake

trait SayHelloComponent {
  val sayHelloService: SayHelloService

  trait SayHelloService {
        def sayHello:Unit
  }
}
