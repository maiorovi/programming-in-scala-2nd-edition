package cake

object ComponentRegistry  extends SayHelloComponentImpl with NameProviderComponentImpl with GreetingsProviderComponentImpl {

  override val nameProvider = new NameProviderImpl
  override val sayHelloService = new SayHelloServiceImpl
  override val greetingsProvider = new GreetingProviderImpl
}
