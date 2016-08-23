// Figure 1, page 3
trait SomethingNew {
  type Q
  // type of a heap
  type Z

}


/*
import org.scalacheck.Gen._
import org.scalacheck.Gen


val g = choose(-2, 5)

println( g.sample )

val genString = for {
  c1 <- Gen.numChar
  c2 <- Gen.alphaUpperChar
  c3 <- Gen.alphaLowerChar
  c4 <- Gen.alphaChar
  c5 <- Gen.alphaNumChar
} yield List(c1,c2,c3,c4,c5).mkString


genString.sample
*/
