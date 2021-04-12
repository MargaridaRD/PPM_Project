package projeto

import projeto.Color.Color
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
    def rSlice(lst: List[List[Int]]): List[List[Int]] = {
      lst match {
        case List() => Nil
        case xs :: xss => (xs.splitAt(xs.length / 2)._2 :: rSlice(xss))
      }
    }
    def lSlice(lst: List[List[Int]]):( List[List[Int]])={
      lst match {
        case List() => Nil
        case xs::xss => (xs.splitAt(xs.length/2)._1 ::lSlice(xss))
      }
    }
    (lSlice(lst),rSlice(lst))
  }

  def verticalSlice2(lst: List[List[Int]]): (List[List[Int]], List[List[Int]]) = {
    def aux(lst: List[List[Int]], side: Int): List[List[Int]] = {
    lst match {
      case List() => Nil
      case xs :: xss => (xs.splitAt(xs.length / 2)._1 :: aux(xss, side))
    }
    }
    (aux(lst, 1), aux(lst, 2))
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


  def mirrorV (qt:QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty => QEmpty
      case QLeaf(s: Section) => QLeaf(s)
      case QNode(value, l1, l2, l3, l4) => QNode(value, l2, l1, l4, l3)
    }
  }


  def mirrorH (qt:QTree[Coords]):QTree[Coords]={
      qt match {
        case QEmpty => QEmpty
        case QLeaf(s: Section) => QLeaf(s)
        case QNode(value, l1, l2, l3, l4) => QNode(value, l3, l4, l1, l2)
      }
  }

  def dimensions(qt: QTree[Coords]): (Int, Int) = {
    qt match {
      case QNode(value, l1, l2, l3, l4) => (value._2)
    }
  }

  def swapCoords(c: Coords): Coords = {
    ((c._1._1, c._2._2),(c._2._1, c._1._2))
  }

  def rotateCoords(c: Coords, height: Int): Coords = {
    ((height - c._1._2, c._1._1), (height - c._2._2, c._2._1))
  }


  def rotateD(qt:QTree[Coords]): QTree[Coords]={
    def aux(qt_aux: QTree[Coords], height: Int): QTree[Coords] = {
    qt_aux match {
      case QEmpty => QEmpty
      case QLeaf(s: Section) => QLeaf(rotateCoords(swapCoords(s._1), height), s._2)
      case QNode(value, l1, l2, l3, l4) => QNode(rotateCoords(swapCoords(value), height), aux(l3, height), aux(l1, height), aux(l4, height), aux(l2, height))
    }
    }
    aux(qt, dimensions(qt)._2)
  }

  def mapColorEffect(f:Color => Color, qt:QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty=>QEmpty
      case QLeaf(s:Section) =>QLeaf((s._1,f(s._2)))
      case  QNode(a, l1, l2, l3, l4)=>QNode(a, mapColorEffect(f,l1), mapColorEffect(f,l2), mapColorEffect(f,l3), mapColorEffect(f,l4))
    }

  }


  def main(args: Array[String]): Unit = {
    val teste = makeTree( ImageUtil.readColorImage("src/projeto/img/4cores15_10_V.png"))
    println("A ARVORE É " + teste)
   // println("O MAXIMO PIXEL É " + maximum(teste))
    //val rotate = rotateD(teste)
   // println("O ESPELHO É " + mirror_V)
    //ImageUtil.writeImage(makeBitMap(mirror_V), "src/projeto/img/testeMirrH.png", "png")
    ImageUtil.writeImage(makeBitMap(rotateD(teste)), "src/projeto/img/testeRotate3.png", "png")
  }


}