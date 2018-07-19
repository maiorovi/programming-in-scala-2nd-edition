package concurrency_in_scala.chapter3.filesystem

sealed trait State
class Idle extends State
class Creating extends State
class Copying(val n: Int) extends State
class Deleting extends State
