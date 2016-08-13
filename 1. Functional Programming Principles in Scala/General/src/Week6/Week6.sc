// Lecture 5.1

def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new Error("init pf empty list")
  case List(x) => List()
  case y::ys => y::init(ys)
}

val l = List(1, 5, 7, 8)
val r = init(l)


val rm = List('a', 'b', 'c', 'd')
def removeAt[T](n: Int, xs: List[T]): List[T] = xs match  {
  case List() => throw new Error("Can't remove n'th element from empty list")
  case y::ys => if (n > 0) y::removeAt(n-1, ys) else ys
}

def flatten(xs: List[Any]) : List[Any] = xs match {
  case List() => xs
  case h::tail => {
    h match {
      case x:List[Any] => flatten(x)++flatten(tail)
      case _ => h::flatten(tail)
    }
  }
}

def msort[T](xs: List[T])(implicit ord: Ordering[T]) : List[T] = {
  val n = xs.length / 2;
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (x:List[T], Nil) => x
      case (Nil, y:List[T]) => y
      case (x::xz, y::yz) =>  if ( ord.lt(x,y)) x::merge(xz, ys) else y::merge(xs, yz)
    }
    val (fst, snd) = xs splitAt n
    merge( msort(fst), msort(snd))
  }
}

val r2 = flatten(List(List(1, 1), 2, List(3, List(5, 8, List(4, 7)))))
val l3 = msort(r)

val fruits = List("apple", "pineapple", "organge", "bannana" )

println( msort(fruits) )
//println( removeAt(1, rm ))


def squareList(xs: List[Int]): List[Int] =
  xs match {
    case Nil => Nil
    case y :: ys => y*y ::squareList(ys)
  }

def squareList2(xs: List[Int]): List[Int] = xs map (x => x*x)

