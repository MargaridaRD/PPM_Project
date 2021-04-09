package projeto

import projeto.Coords.Coords
import projeto.Point.Point

import scala.annotation.tailrec


case class Tree(myField: QTree[Coords]) {

}

object Tree{

  def makeList(ar: Array[Array[Int]]): List[List[Int]] = {
    val lst = ar.toList
    lst map (x => x.toList)
  }
  //verifica se todos os pixeis são iguais para um quadrante
  @tailrec
  def verify_pixels(lst:List[List[Int]], p:Int):Boolean={
     lst match{
     case Nil => true
     case List(List(_))=> true
     case y::ys  =>   println("y: " + y + "p: " + p )
       if( y.takeWhile(x=> x==p).length == y.length) verify_pixels(ys,p) else false }}

  // length -1 nao é preciso porque é do quadrante a seguir
  def makeTree(lst: List[List[Int]], p:Point): QTree[Coords] = {
    lst match {
      case Nil => QEmpty
      case _ =>
        if( verify_pixels(lst))  {
          // QLeaf[Coords, Section] = QLeaf((((0,0):Point,(0,0):Point):Coords, Color.red):Section)
          QLeaf( ((p,(p._1 + lst.head.length - 1, p._2 + lst.length - 1)), ImageUtil.decodeRgb(lst.head.head)))
        }
        else {
          QNode((p, (p._1 + lst.head.length - 1, p._2 + lst.length - 1)),
            makeTree(verticalSlice(horizontalSlice(lst)._1)._1, p),
            makeTree(verticalSlice(horizontalSlice(lst)._1)._2, (p._1 + lst.head.length / 2, p._2)),
            makeTree(verticalSlice(horizontalSlice(lst)._2)._1, (p._1, p._2 + lst.length / 2)),
            makeTree(verticalSlice(horizontalSlice(lst)._2)._2, (p._1 + lst.head.length / 2, p._2 + lst.length / 2)))
        }
    }
  }


  def horizontalSlice(lst: List[List[Int]]): (List[List[Int]], List[List[Int]]) = {
    lst match {
      case List() => (Nil, Nil)
      case xss => xss.splitAt(xss.length/2)
    }
  }
  def verticalSlice(lst: List[List[Int]]): (List[List[Int]], List[List[Int]]) = {

    lst match {
      case List() => (Nil, Nil)
      case xs::xss => (xs.splitAt(xs.length/2)._1 :: verticalSlice(xss)._1, xs.splitAt(xs.length/2)._2 :: verticalSlice(xss)._2)
    }
  }

  def main(args: Array[String]): Unit = {
    val array = makeList( ImageUtil.readColorImage("src/projeto/objc2_2.png"))
    val teste= makeTree(array,(0,0))
    println("teste: " + teste )
  }


}

