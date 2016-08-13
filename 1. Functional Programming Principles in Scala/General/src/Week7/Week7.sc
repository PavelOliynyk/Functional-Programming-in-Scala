val number: Option[Int] = Some(3)
val noNumber: Option[Int] = None
val result1 = number.fold(0)(_ * 3)
val result2 = noNumber.fold(0)(_ * 3)


object Planets extends Enumeration {
  val Mercury = Value
  val Venus = Value
  val Earth = Value
  val Mars = Value
  val Jupiter = Value
  val Saturn = Value
  val Uranus = Value
  val Neptune = Value
  val Pluto = Value
}


Planets.Mercury