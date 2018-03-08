package cake

trait GreetingsProviderComponent {
  val greetingsProvider: GreetingsProvider

  trait GreetingsProvider {
    def provide:String
  }
}
