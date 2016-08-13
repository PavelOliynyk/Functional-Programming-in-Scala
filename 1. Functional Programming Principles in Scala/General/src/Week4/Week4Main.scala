package Week4

/**
  * Created by paveloliynyk on 31/07/2016.
  */
object  Week4Main {

  def main(args: Array[String]): Unit = {

    def isort(xs:List[Int]) : List[Int] = xs match {
      case List() => List()
      case y::ys => insert(y, isort(ys))
    }

    def insert(x: Int, xs: List[Int]): List[Int] = xs match {
      case List() => x::xs
      case y :: ys =>
        if (x < y) x::insert(y, ys)
        else  y::insert(x, ys)

    }

    val a = List(7, 3, 9, 2)

    val r = isort( a )


    println( r )

  }



}
