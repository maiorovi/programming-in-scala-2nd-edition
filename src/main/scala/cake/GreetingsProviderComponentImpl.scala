package cake

trait GreetingsProviderComponentImpl extends GreetingsProviderComponent{

  class GreetingProviderImpl extends GreetingsProvider {
    override def provide: String = "Good Luck."
  }

}
