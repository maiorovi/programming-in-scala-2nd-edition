package cake

trait SayHelloComponentImpl  extends SayHelloComponent {
  this: SayHelloComponentImpl with NameProviderComponent with GreetingsProviderComponentImpl =>

  class SayHelloServiceImpl extends SayHelloService {
    override def sayHello: Unit = println(s"Hello ${nameProvider.getName}! I wish you ${greetingsProvider.provide}")
  }


}
