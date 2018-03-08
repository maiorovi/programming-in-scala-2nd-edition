package cake

trait NameProviderComponent {
  val nameProvider: NameProvider


  trait NameProvider {
    def getName:String
  }
}
