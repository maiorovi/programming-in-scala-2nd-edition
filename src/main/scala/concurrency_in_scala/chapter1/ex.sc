// first exercise
def compose[A, B, C](g: B => C, f: A => B): A => C = a => g(f(a))


val f: Int => Int = compose[Int, Int, Int](x => x * 2, y => y + 2 )

f(2)

//2.

def fuse[A, B] (a: Option[A], b: Option[B]): Option[ (A, B) ] = for(x <- a; y <- b)
  yield (x, y)

fuse(Some("qqq"), Some("bbbbb"))

//3.
def check[T](xs: Seq[T])(pred: T => Boolean):Boolean = xs.forall(pred)

check(1 until 10) { 40 / _ > 0 }

//4.
class Pair[A, B](a: A, b: B)

5.

def permutations(x: String): Seq[String] = {
  def loop(x: String, curr: String, acc:List[String]):Seq[String] = {
    if (curr.length == x.length) {
      return curr::acc
    }

    x.flatMap(ch => {
      if (!curr.contains(ch)) {
        loop(x, curr + ch, acc)
      }  else acc
    }).toList
  }

  loop(x, "", Nil)
}

permutations("abcd")
//6.
def combinations(n: Int, xs: Seq[Int]): Iterator[Seq[Int]] = ???