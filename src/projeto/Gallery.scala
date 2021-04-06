package projeto

import projeto.Coords.Coords
import sun.jvm.hotspot.utilities.BitMap


case class QNode[A](value: A, one: QTree[A],
                    two: QTree[A],three: QTree[A],
                    four: QTree[A]) extends QTree[A]{

}

case class Gallery  (myField:QTree[Coords]){}

object Gallery {

  def makeQTree(map: BitMap): QTree

}



