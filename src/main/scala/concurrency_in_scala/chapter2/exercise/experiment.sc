def printA(a: String, b:String): Unit = {
  println(a)
}

printA("a here", {
  println("inside")
  "dfg"
})