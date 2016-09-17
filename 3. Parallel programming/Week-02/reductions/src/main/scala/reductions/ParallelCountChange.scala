package reductions

import org.scalameter._
import common._

object ParallelCountChangeRunner {

  @volatile var seqResult = 0

  @volatile var parResult = 0

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 80,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val amount = 250
    val coins = List(1, 2, 5, 10, 20, 50)
    val seqtime = standardConfig measure {
      seqResult = ParallelCountChange.countChange(amount, coins)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential count time: $seqtime ms")

    def measureParallelCountChange(threshold: ParallelCountChange.Threshold, t: String): Unit = {
      val fjtime = standardConfig measure {
        parResult = ParallelCountChange.parCountChange(amount, coins, threshold)
      }
      println(s"parallel result = $parResult : $t")
      println(s"parallel count time: $fjtime ms")
      println(s"speedup: ${seqtime / fjtime}")
    }

    measureParallelCountChange(ParallelCountChange.moneyThreshold(amount), "money")
    measureParallelCountChange(ParallelCountChange.totalCoinsThreshold(coins.length), "totalCoins")
    measureParallelCountChange(ParallelCountChange.combinedThreshold(amount, coins), "Combined")
  }
}

object ParallelCountChange {

  /** Returns the number of ways change can be made from the specified list of
   *  coins for the specified amount of money.
   */
  def countChange(money: Int, coins: List[Int]): Int = {

    def variations(capacity: Int, change: List[Int]): Int = {

      if(capacity == 0)
        1
      else if(capacity>=1 && change.isEmpty)
        0
      else if(capacity < 0)
        0
      else
        variations(capacity, change.tail) + variations(capacity - change.head, change)

    }

    variations( money, coins.sortWith(_ <  _) )
  }

  type Threshold = (Int, List[Int]) => Boolean

  /** In parallel, counts the number of ways change can be made from the
   *  specified list of coins for the specified amount of money.
   */
  def parCountChange(money: Int, coins: List[Int], threshold: Threshold): Int = {
    if ( threshold( money, coins ) == true )
      countChange( money, coins)
    else {
      if(money == 0)
        1
      else if(money >=1 && coins.isEmpty)
        0
      else if(money < 0)
        0
      else {
        val change = coins.sortWith(_ < _)
        val (l, r) = parallel( parCountChange(money, change.tail, threshold ), parCountChange( money - change.head, change, threshold ) )
        l + r
      }
    }
  }

  /** Threshold heuristic based on the starting money. */
  def moneyThreshold(startingMoney: Int): Threshold = {
    def f(l: Int, list: List[Int]) : Boolean = (l <= (startingMoney * 2 / 3) )
    f
  }


  /** Threshold heuristic based on the total number of initial coins. */
  def totalCoinsThreshold(totalCoins: Int): Threshold = {
    def f(l: Int, list: List[Int]) : Boolean = (l <= (totalCoins * 2 / 3) )
    f
  }



  /** Threshold heuristic based on the starting money and the initial list of coins. */
  def combinedThreshold(startingMoney: Int, allCoins: List[Int]): Threshold = {
    def f(l: Int, list: List[Int]): Boolean =
        if ( (moneyThreshold(startingMoney) == true) || totalCoinsThreshold(list.length) == true) true
        else false
    f
  }
}
