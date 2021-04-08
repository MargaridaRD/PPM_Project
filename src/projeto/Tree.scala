package projeto

import projeto.Coords.Coords
import projeto.Point.Point


case class Tree(myField: QTree[Coords]) {

  def makeList(ar: Array[Array[Int]]): List[List[Int]] = {
    val lst = ar.toList
    lst map (x => x.toList)
  }

  def makeTree(lst: List[List[Int]], p:Point): QTree[Coords] = {
    lst match {
      case List(List()) => QEmpty
      case List(xs) =>
      case xss =>QNode((p, ((p._1+xss(1).length-1 ),(p._2 + xss.length-1 ) )),
          makeTree(verticalSlice(horizontalSlice(xss)._1)._1, p),
          makeTree(verticalSlice(horizontalSlice(xss)._1)._2,(p._1+xss(1).length/2-1,p._2)),
          makeTree(verticalSlice(horizontalSlice(xss)._2)._1, (p._1,p._2+xss.length/2-1)),
          makeTree(verticalSlice(horizontalSlice(xss)._2)._2, ((p._1+xss(1).length/2-1),p._2+xss.length/2-1)))
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
  def generate(s: String): QTree[Coords] = {
    val array = ImageUtil.readColorImage(s)
    val list = makeList(array)

  }
*/

}

