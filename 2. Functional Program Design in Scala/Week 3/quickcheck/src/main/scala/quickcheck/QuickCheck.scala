package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for{
    a <- arbitrary[Int]
    h <- oneOf( const(empty), genHeap)
  } yield insert(a, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  lazy val genMap: Gen[Map[Int,Int]] = for {
    k <- arbitrary[Int]
    v <- arbitrary[Int]
    m <- oneOf(const(Map.empty[Int,Int]), genMap)
  } yield m.updated(k, v)

  property("initial test") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("when add element to empty and find min") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("when insert two elements into heap") = forAll { (e1: Int, e2: Int) =>
    val h = insert(e2,  insert(e1, empty))
    val s = if (e1 > e2) e2 else e1
    findMin(h) == s
  }

  property("when insert element into empty heap and delet it after") = forAll { a: Int =>
    val h = insert(a, empty)
    val r = deleteMin(h)
    r == empty
  }

  property("find min elements from two heaps; meld heaps; meld heap min should be one of min") = forAll { (a: H, b: H) =>
    val ma = findMin(a)
    val mb = findMin(b)
    val result = meld(a, b)
    val minMeld = findMin(result)
    minMeld == ma || minMeld == mb
  }

  property("findAndRemove") = forAll { h: H =>

    def removeMinAcc(in: H, ls: List[Int]): List[Int] = {
      if (isEmpty(in)) ls
      else findMin(in) :: removeMinAcc( deleteMin(in), ls)
    }
    val someResult = removeMinAcc(h, Nil)
    someResult == someResult.sorted
  }

  property("meld min all") = forAll { (a: H, b: H) =>

    def removeMinAcc(in: H, ls: List[Int]): List[Int] = {
      if (isEmpty(in)) ls
      else findMin(in) :: removeMinAcc( deleteMin(in), ls)
    }

    val meldA = meld(a, b)
    val minA = findMin(a)
    val meldB = meld(deleteMin(a), insert(minA, b))

    val r1 = removeMinAcc(meldA, Nil)

    val r2 = removeMinAcc(meldB, Nil)
    r1 == r2
  }

}
