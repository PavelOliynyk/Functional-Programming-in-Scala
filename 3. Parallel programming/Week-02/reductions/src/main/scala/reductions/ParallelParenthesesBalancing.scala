package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {

    def harvest(food: List[Char], accumulator: List[Char], searchMode: Int): List[Char] = food match {
      case List() => if (searchMode == 0)
        accumulator
      else
        Nil
      case c :: tail =>
        c match {
          case '(' => {
            if (searchMode == 0 && accumulator.length == 1 && c == accumulator(0))
              Nil
            else {
              var res = harvest(tail, List(c), searchMode + 1)
              List.concat(res, accumulator)
            }
          }
          case ')' => {
            if (searchMode == 0 && accumulator.length == 1 && c == accumulator(0))
              Nil
            else {
              val res = harvest(tail, List(c), searchMode - 1)
              List.concat(accumulator, res)
            }
          }
          case _ => {
            harvest(tail, c :: accumulator, searchMode)
          }
        }
    }

    if ( chars.isEmpty)
      true

    else if (chars.length == 2)

      (chars(0) == '(' && chars(1) == ')' )

    else {

      val resultStr = harvest(chars.toList, List(), 0)

      resultStr.length > 0 && chars.length == resultStr.length

    }
  }
  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int) /*: ???*/ = {
      ???
    }

    def reduce(from: Int, until: Int) /*: ???*/ = {
      ???
    }

    reduce(0, chars.length) == ???
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
