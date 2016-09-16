import scalashop.{HorizontalBoxBlur, Img}

val w = 3
val h = 3
val src = new Img(w, h)
val dst = new Img(w, h)
src(0, 0) = 0; src(1, 0) = 1; src(2, 0) = 2
src(0, 1) = 3; src(1, 1) = 4; src(2, 1) = 5
src(0, 2) = 6; src(1, 2) = 7; src(2, 2) = 8

HorizontalBoxBlur.blur(src, dst, 0, 2, 1)

for(x <- 0 to dst.width - 1)
{
  for(y <- 0 to dst.height - 1)
  {
    print( dst(x, y) + " ")
  }
  println(" ")
}

//check(0, 0, 2)
//check(1, 0, 2)
//check(2, 0, 3)
//check(0, 1, 3)
//check(1, 1, 4)
//check(2, 1, 4)
//check(0, 2, 0)
//check(1, 2, 0)
//check(2, 2, 0)