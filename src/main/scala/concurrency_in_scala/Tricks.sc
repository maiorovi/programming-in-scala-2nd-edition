def runTwice(body: => Unit): Unit = {
  body
  body
}


runTwice {
  println("Hello")
}

for(i <- 0 until 10) println(i)