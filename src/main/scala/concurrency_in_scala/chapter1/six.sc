import scala.annotation.tailrec

def factorial(x: Int): Int = {
  @tailrec
  def loop(x:Int, acc:Int = 1): Int = {
     if (x == 1) {
      return acc
    }

    return loop(x-1, acc*x)
  }

  return if (x == 0) {
    1
  }  else {
    loop(x)
  }
}

def subsequences(n:Int, subSeqSize: Int) =
  factorial(n) / (factorial(n - subSeqSize) * factorial(subSeqSize))


def toOnes(n: Int) : String = {
  def loop(n: Int, sb: StringBuilder): String = {
    if (n == 0) {
      return sb.toString()
    }

    loop(n-1, sb.append("1"))
  }

  loop(n, new StringBuilder)
}




def padLeft(s:String, chToPad:String, targetLength: Int):String = {
  if  (s.length >= targetLength) {
    return s
  }

  return padLeft(chToPad+ s, chToPad, targetLength)
}

def padRight(s:String, chToPad:String, targetLength: Int):String = {
  if  (s.length >= targetLength) {
    return s
  }

  return padRight(s + chToPad, chToPad, targetLength)
}


implicit class RichChar(x: Char) {
  implicit def toIntValueMy: Int = x.toInt - '0'
}

def binaryToDecimal(s: String): Int =
  (0 until s.length).map(i => s.charAt(i).toIntValueMy * Math.pow(2, i)).sum.toInt

def combinations(n: Int, xs: Seq[Int]): Iterator[Seq[Int]] = {
  val ones= toOnes(n)
  val start = binaryToDecimal(padRight(ones, "0", xs.length))
  val end = binaryToDecimal(padLeft(ones, "0", xs.length))

  (for(x <- start to end if toBinary(x).hasNOnesInBinaryForm(n)) yield getCombination(x, xs)).toIterator
}

def toBinary(x: Int): String = {
  return if (x == 0) {
    ""
  } else {
    (x % 2).toString + toBinary(x / 2)
  }
}

toBinary(10)

def getCombination(x: Int, xs: Seq[Int]): Seq[Int] = {
  val binaryForm = toBinary(x)

  binaryForm.zipWithIndex
    .filter( t => t._1 == '1')
    .map(t => t._2)
    .map(t => xs(t))
}


getCombination(3 ,Seq(1, 4, 9, 16))


combinations(2, Seq(1, 4, 9, 16)).foreach(println)

implicit class MyStringWrapper(x: String) {
  def hasNOnesInBinaryForm(n: Int) = x.count(_ == '1') == n

}


"111".hasNOnesInBinaryForm(3)
