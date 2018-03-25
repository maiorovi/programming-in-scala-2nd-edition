import java.math.BigInteger

val big = new BigInteger("12345")

val greetStrings = new Array[String](3)


greetStrings(0) = "Hello"
greetStrings(1) = ", "
greetStrings(2) = "world!\n"

for(i <- 0 to 2) {
  print(greetStrings(i))
}

val numNames = Array("zero", "one", "two")

val oneTwoThree = List(1, 2, 3)

oneTwoThree(1)

oneTwoThree.filterNot(_ == 2)

oneTwoThree.sortWith((x,y) => x > y)
val strings = List("c", "b", "a")

