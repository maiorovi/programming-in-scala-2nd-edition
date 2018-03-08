var capital = Map("US" -> "Washington", "France" -> "Paris")

capital += ("Japan" -> "Tokyo")

println(capital)

def factorial(x: BigInt): BigInt =
  if (x == 0) 1 else x * factorial(x - 1)


factorial(30)

