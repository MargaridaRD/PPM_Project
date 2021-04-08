package projeto

import projeto.Coords.Coords

case class Tree(myField: QTree[Coords]) {

  def makeList(ar: Array[Array[Int]]): List[List[Int]] = {
    val lst = ar.toList
    lst map (x => x.toList)
  }

  def makeTree(lst: List[List[Int]]): QTree[Coords] = {
    lst match {
      case List(List()) => QEmpty
      case List(xs) =>
      case xss => makeTree(verticalSlice(horizontalSlice(xss)._1)._1)
    }
  }

  def horizontalSlice(lst: List[List[Int]]): (List[List[Int]], List[List[Int]]) = {
    lst match {
      case List(List()) => (Nil, Nil)
      case xss => xss.splitAt(xss.length/2)
    }
  }
  def verticalSlice(lst: List[List[Int]]): (List[List[Int]], List[List[Int]]) = {
    lst match {
      case List(List()) => (Nil, Nil)
      case xss => xss.splitAt(xss.length/2)
      case xs::xss => (((xs.splitAt(xs.length/2))._1 :: verticalSlice(xss)._1), ((xs.splitAt(xs.length/2))._2 :: verticalSlice(xss)._2))

    }
  }
 /* def verticalSlice(lst: List[List[Int]]): (List[List[Int]], List[List[Int]]) = {
    def aux (l: List[List[Int]], f: (List[List[Int]], List[List[Int]])): (List[List[Int]], List[List[Int]]) = {
      l match {
        case List(List()) => f
        case xss => xss.splitAt(xss.length / 2)
        case xs :: xss => aux(xss, (((xs.splitAt(xs.length / 2))._1 :: f._1), ((xs.splitAt(xs.length / 2))._2 :: f._2)))

      }
    }
    aux(lst, (Nil,Nil))

  }
*/
  def generate(s: String): QTree[Coords] = {
    val array = ImageUtil.readColorImage(s)
    val list = makeList(array)

  }


}

