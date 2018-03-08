package cake

trait NameProviderComponentImpl extends NameProviderComponent {
  class NameProviderImpl extends NameProvider {
    override def getName: String = "Egor M"
  }
}
