
/*
abstract class Nat {
  def isZero: Boolean
  def predecessor: Nat
  def successor: Nat = new Succ(this)
  def + (that: Nat): Nat
  def - (that: Nat): Nat
}

object Zero extends Nat {
  def isZero: Boolean = true
  def predecessor: Nat = throw new Error("0.predecessor")

  def + (that: Nat): Nat = that
  def - (that: Nat): Nat = if ( that.isZero) this else throw new Error("Negative number")
}

class Succ(n: Nat) extends Nat {
  def isZero: Boolean = false
  def predecessor: Nat = n

  def + (that: Nat): Nat = new Succ(n + that)
  def - (that: Nat): Nat = if (that.isZero) this else n - that.predecessor
}

trait List[T] {
  def isEmpty : Boolean
  def head: T
  def tail : List[T]
}

object List {
  // List(1, 2)
  def apply[T](x1: T, x2:T) List[T] = new   
}


trait Expr {
  def show(e:Expr ) : String = e match {
    case Var(x) => x.toString
    case Number(n) => n.toString()
    case Sum(e1, e2) => "{" + show( e1 ) + " + " + show( e2 ) + "}"
    case Sum(Prod(z, Var(x)), Var(y)) => show(z) + "*" + show(x) + " + " + show(y)
    case Prod( Sum(z, Var(x)), Var(y)) => "(" + show(z) + "+" + show(x) + ") * " + show(y)
  }
}


case class Number(n: Int) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
case class Prod(e1: Expr, e2: Expr) extends Expr
case class Var(e:Expr)  extends  Expr
*/

def isort(xs:List[Int]) : List[Int] = xs match {
  case List() => List()
  case y::ys => insert(y, isort(ys))
}

def insert(x: Int, xs: List[Int]): List[Int] = xs match {
  case List() => xs
  case y :: ys =>
          if (x > y) x::insert(y, ys)
          else  y::insert(x, ys)

}

val a = List(7, 3, 9, 2)



println( isort( a ) )
