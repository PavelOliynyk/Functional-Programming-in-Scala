
def upsweepSequential(input: Array[Float], from: Int, until: Int): Float = {
  val result = { for
  {
    x <- from to until - 1
  } yield if (x > 0 ) ((input(x) / x )) else 0
  }.toList.max
  result
}

val res = upsweepSequential(Array[Float](0f, 1f, 8f, 9f), 1, 4)


