import patmat.Huffman.{Fork, Leaf, _}

val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))

val r = combine(leaflist)
//List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))