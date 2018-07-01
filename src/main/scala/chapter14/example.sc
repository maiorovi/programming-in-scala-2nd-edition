import scala.annotation.tailrec

val list = List(1,2,3,4,5)

def sum(l: List[Int]): Int = l match {
  case head::tail => head + sum(tail)
  case Nil => 0
}

sum(list)


def tailRecSum(l: List[Int]): Int = {
  @tailrec
  def sum(acc: Int, l: List[Int]): Int = l match {
    case head::tail => sum(acc + head, tail)
    case Nil => acc
  }

  sum(0, l)
}

tailRecSum(list)

def fib(n:Int): Int = {
  if(n == 0) {
    return 0
  }

  if(n == 1) {
    return 1
  }

  fib(n-1) + fib(n-2)
}

fib(3)

def fibTailRec(n: Int): Int = {
  @tailrec
  def loop(f: Int, s: Int, curr: Int, target: Int): Int = if (curr < target) {
    loop(s, f+ s, curr+1, target)
  } else {
    f
  }

  loop(0, 1, 0, n)
}

fibTailRec(20)