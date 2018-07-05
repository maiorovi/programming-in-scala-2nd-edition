object Demo {
  val x = { println("initializing x"); "done"}
}

Demo
println("----------")
Demo.x

object LazyDemo {
  lazy val x = { println("initializing x"); "done"}
}

LazyDemo

println("----------")

LazyDemo.x