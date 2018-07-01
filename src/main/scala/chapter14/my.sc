val l = List(1,2):::List(3,4,5)

l.+:(10)



def concat[T](xs: List[T], ys: List[T]): List[T] =  xs match {
  case Nil => ys
  case z::zs => z::concat(zs, ys);
}

def length[T](xs:List[T]): Int = xs match {
  case x::xs => 1 + length(xs)
  case Nil => 0;
}

def reverse[T](xs:List[T]):List[T] = {
  def loop(ys:List[T], acc:List[T]):List[T] = ys match {
    case z::zs => loop(zs, z::acc)
    case Nil => acc
  }

  loop(xs, Nil)
}


reverse(List(1,2,3,4))

length(List(1,2,3,4))

concat(List(1,2), List(3,4,5))


List(1,2,3,4) drop 1

List(1,2,3,4) take 3

def flatten[T](xs:List[List[T]]):List[T] = xs match {
  case x::xs => x:::flatten(xs)
  case Nil => Nil
}

flatten(List(List(1,2), List(3,4)))

def sum(xs: List[Int]): Int = (0 /: xs)(_ + _)

def reverseLeft[T](xs:List[T]): List[T] = (List[T]() /: xs) ( (acc,x) => x::acc )