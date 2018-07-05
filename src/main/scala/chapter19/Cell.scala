package chapter19

class Cell[T](init: T) {
  private[this] var current = init

  def get = current

  def set(x:T) = { current = x}
}
