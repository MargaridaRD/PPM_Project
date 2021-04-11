package projeto

import projeto.Coords.Coords
import projeto.Point.Point
import projeto.Section.Section
import scala.annotation.tailrec


case class Tree(myField: QTree[Coords]) {

}

object Tree{

  //MAKE TREE
  //verifica se todos os pixeis são iguais para um quadrante
  def verify_pixels(lst:List[List[Int]]):Boolean={
    @tailrec
    def aux(l:List[Int]):Boolean = {
      l match {
        case Nil => true
        case List(_)=> true
        case y :: ys :: yss => if (y == ys) aux(ys::yss) else false
      }
    }

    aux(lst.flatten)
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

  // length -1 nao é preciso em todos porque é do quadrante a seguir
  def makeTree(ar: Array[Array[Int]]): QTree[Coords] = {
    def aux(lst: List[List[Int]], p: Point): QTree[Coords] = {
      lst match {
        case Nil => QEmpty
        case List(List())=> QEmpty
        case _ =>
          if (verify_pixels(lst)) {
            QLeaf(((p, (p._1 + lst.head.length - 1, p._2 + lst.length - 1)), ImageUtil.decodeRgb(lst.head.head).toList))
          }
          else {
            QNode((p, (p._1 + lst.head.length - 1, p._2 + lst.length - 1)),
              aux(verticalSlice(horizontalSlice(lst)._1)._1, p),
              aux(verticalSlice(horizontalSlice(lst)._1)._2, (p._1 + lst.head.length / 2, p._2)),
              aux(verticalSlice(horizontalSlice(lst)._2)._1, (p._1, p._2 + lst.length / 2)),
              aux(verticalSlice(horizontalSlice(lst)._2)._2, (p._1 + lst.head.length / 2, p._2 + lst.length / 2)))
          }
      }
    }
    aux(ar.toList map (x => x.toList),(0,0))
  }

  //MAKE BITMAP

  // glue junta verticalmente l1+l2 e l3+l4 e junta horizontalmente o resultado das duas
  def glue(l1: List[List[Int]], l2: List[List[Int]],l3: List[List[Int]], l4: List[List[Int]]): List[List[Int]] = {
    def glue_vertical(l1: List[List[Int]], l2: List[List[Int]]): List[List[Int]] = {
      (l1, l2) match {
        case (Nil, Nil) => Nil
        case (_, Nil) => l1
        case (Nil, _) => l2
        case (x :: xs, y :: ys) => (x foldRight y) (_:: _) :: glue_vertical(xs, ys)
      }
    }
    glue_vertical(l1, l2) ::: glue_vertical(l3, l4)
  }


  def leafToList(section: Section): List[List[Int]] = {
    val x: Int = section._1._2._1 - section._1._1._1 + 1
    val y: Int = section._1._2._2 - section._1._1._2 + 1
    val cor = ImageUtil.encodeRgb(section._2.head,section._2(1),section._2(2))
    List.fill(x*y)(cor).grouped(x).toList
  }

  def makeBitMap(qTree:QTree[Coords]):Array[Array[Int]]= {
    def aux(qTree:QTree[Coords]):List[List[Int]]= {
      qTree match {
        case QEmpty => Nil
        case QLeaf(s: Section) => leafToList(s)
        case QNode(_, l1, l2, l3, l4) => glue(aux(l1), aux(l2), aux(l3), aux(l4))

      }
    }
    aux(qTree).toArray map (x => x.toArray)
  }


  def maximum(qt: QTree[Coords]): Int = qt match {
    case QEmpty => 0
    case QLeaf(s: Section) => s._1._2._2
    case QNode(v,l1, l2, l3, l4) => v._2._2.max(maximum(l1) max maximum(l2) max maximum(l3) max maximum (l4))
  }


  def swap(coords: Coords, length: Int): Coords = {
    val p1: Point = (length-coords._2._1, coords._1._2)
    val p2: Point = (length-coords._1._1, coords._2._2)
    (p1, p2)
  }


  def mirrorV (qt:QTree[Coords]):QTree[Coords]={
    def aux(qt:QTree[Coords], max: Int):QTree[Coords]={
    qt match {
      case QEmpty => QEmpty
      case QLeaf(s: Section) => QLeaf(swap(s._1, max),s._2)
      case QNode(value, l1, l2, l3, l4) => QNode(swap(value, max), aux(l2, max), aux(l1, max), aux(l4, max), aux(l3, max))
    }
    }
    aux(qt, maximum(qt))
  }


  def main(args: Array[String]): Unit = {
    val teste = makeTree( ImageUtil.readColorImage("src/projeto/img/retver.png"))
    //println("A ARVORE É " + teste)
   // println("O MAXIMO PIXEL É " + maximum(teste))
    val mirror_V = mirrorV(teste)
   // println("O ESPELHO É " + mirror_V)
    ImageUtil.writeImage(makeBitMap(mirror_V), "src/projeto/img/testeMirrV.png", "png")
  }


}