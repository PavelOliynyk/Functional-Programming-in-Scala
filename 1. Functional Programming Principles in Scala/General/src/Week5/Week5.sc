def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  (xs foldRight List[U]())( (x,y) => f(x)::y )

mapFun(List(1,2,3) , (x: Int) => x * x)

//def lengthFun[T](xs: List[T]): Int =
//  (xs foldRight 0)( ??? )

val nums = List(2, -4, 5, 7, 1)

nums reduceLeft( (x, y) => x + y)

val fruits = List("apple", "pineapple", "organge", "bannana" )

nums filter  (x => x > 0)

nums filterNot ( x => x > 0)

nums partition(x => x > 0 )

nums takeWhile( x => x > 0)

nums dropWhile( x => x >  0)

nums span ( x => x > 0)

def pack[T](xs: List[T]) : List[List[T]] = xs match {
  case Nil => Nil
  case x::xs1 => {
    val (first, reset) = xs span  ( y => y == x)
    first :: pack(reset)
  }
}

def encode[T](xs: List[T]): List[ List[(T,  Int)]] =
  pack( xs ) map ( x => List( (x(0), x.length)) )

pack(List("a", "a", "a", "b", "c", "c", "a"))

encode(List("a", "a", "a", "b", "c", "c", "a"))
