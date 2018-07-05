class Food

abstract class Animal {
  type SuitableFood <: Food

  def eat(food: SuitableFood)
}

class Grass extends Food
class Cow extends Animal {
  override type SuitableFood = Grass

  def eat(food: Grass): Unit = println("Cow eats grass")
}