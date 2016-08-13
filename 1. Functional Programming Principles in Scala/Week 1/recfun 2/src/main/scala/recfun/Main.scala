package recfun

import java.util.concurrent.atomic.DoubleAccumulator


object Main {
  def main(args: Array[String]) {
    //println("Pascal's Triangle")
    //for (row <- 0 to 10) {
    //  for (col <- 0 to row)
     //   print(pascal(col, row) + " ")
     // println()
    //}
    //balance("if (zero? x) max (/ 1 x))".toList )
    //balance("())(".toList )

    //countChange(4,List(1,2))

    countChange(300,List(5,10,20,50,100,200,500))

    //countChange(301,List(5,10,20,50,100,200,500))

  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {

      def step(x: Int, y: Int) : Int =
        if (x == 0)
            1
        else if (y == 0)
          1
        else
          step(x - 1, y) + step(x, y - 1)

       step(c, r - 1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {

      def harvest(food: List[Char], accumulator: List[Char], searchMode: Int): List[Char] = food match {
        case List() => if (searchMode == 0)
            accumulator
          else
            Nil
        case c::tail =>
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
              harvest(tail, c::accumulator, searchMode)
            }
        }
      }

      val resultStr = harvest( chars, List(), 0 )

      resultStr.length > 0 &&  chars.length == resultStr.length
    }


  /*
  * Exercise 3
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

      val n = variations(money, coins.sortWith(_ <  _))
      n
    }

  }
