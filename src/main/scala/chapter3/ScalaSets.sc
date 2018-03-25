var jetSet = Set("Boeing", "Airbus")

jetSet += "Lear"

println(jetSet.contains("Cessna"))


import scala.collection.mutable

val treasureMap = mutable.Map[Int, String](1 -> "Go to island.")

treasureMap += (2 -> "Find big X on ground.")
treasureMap += (3 -> "Dig.")

println(treasureMap)



