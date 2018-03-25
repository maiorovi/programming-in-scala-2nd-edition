package chapter4

import chapter4.ChecksumAccumulator.calculate

object Summer {

  def main(args: Array[String]): Unit = {
    for(arg <- args)
      println(s"$arg: ${calculate(arg)}")
  }

}
