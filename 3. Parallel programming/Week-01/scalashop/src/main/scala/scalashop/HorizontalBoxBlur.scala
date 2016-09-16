package scalashop

import org.scalameter._
import common._

object HorizontalBoxBlurRunner {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 5,
    Key.exec.maxWarmupRuns -> 10,
    Key.exec.benchRuns -> 10,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {

    val w = 3
    val h = 3
    val src = new Img(w, h)
    val dst = new Img(w, h)
    src(0, 0) = 0;
    src(1, 0) = 1;
    src(2, 0) = 2
    src(0, 1) = 3;
    src(1, 1) = 4;
    src(2, 1) = 5
    src(0, 2) = 6;
    src(1, 2) = 7;
    src(2, 2) = 8

    HorizontalBoxBlur.blur(src, dst, 0, 2, 1)

    /*
    val radius = 3
    val width = 1920
    val height = 1080
    val src = new Img(width, height)
    val dst = new Img(width, height)
    val seqtime = standardConfig measure {
      HorizontalBoxBlur.blur(src, dst, 0, height, radius)
    }
    println(s"sequential blur time: $seqtime ms")

    val numTasks = 32
    val partime = standardConfig measure {
      HorizontalBoxBlur.parBlur(src, dst, numTasks, radius)
    }
    println(s"fork/join blur time: $partime ms")
    println(s"speedup: ${seqtime / partime}")
    */

  }
}


/** A simple, trivially parallelizable computation. */
object HorizontalBoxBlur {

  /** Blurs the rows of the source image `src` into the destination image `dst`,
   *  starting with `from` and ending with `end` (non-inclusive).
   *
   *  Within each row, `blur` traverses the pixels by going from left to right.
   */
  def blur(src: Img, dst: Img, from: Int, end: Int, radius: Int): Unit = {
    // TODO implement this method using the `boxBlurKernel` method
    for(y <- from to (end - 1)  )
    {
      for (x <- 0 to src.width - 1)
      {
        val p = boxBlurKernel(src, x, y, radius)
        dst(x, y) = p
      }
    }

  }

  /** Blurs the rows of the source image in parallel using `numTasks` tasks.
   *
   *  Parallelization is done by stripping the source image `src` into
   *  `numTasks` separate strips, where each strip is composed of some number of
   *  rows.
   */
  def parBlur(src: Img, dst: Img, numTasks: Int, radius: Int): Unit = {

    val rows :Int = Math.max(src.height / numTasks, 1)

    val start = Range(0, src.height) by rows

    val tasks = start.map(t => {
      task {
        blur(src, dst, t, t + rows, radius)
      }
    })

    tasks.map(t => t.join())
  }

}
