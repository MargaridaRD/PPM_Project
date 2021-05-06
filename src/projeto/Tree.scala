package projeto


import projeto.Coords.Coords
import projeto.Point.Point
import projeto.Section.Section

import scala.annotation.tailrec



case class Tree(myField: String) {
  def imageToTree(): QTree[Coords] = Tree.imageToTree(this.myField)
  def treeToImage(path:String, form:String ,tree:QTree[Coords]):Unit = Tree.treeToImage(path,form,tree)

}

object Tree{

  //MAKE TREE

  def imageToTree(string: String) :QTree[Coords] = {
    val ar=ImageUtil.readColorImage(string)
    makeTree(Bitmap(ar))

  }
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
  def rSlice(lst: List[List[Int]]): List[List[Int]]= {
    lst match {
      case List() => Nil
      case xs :: xss => xs.splitAt(xs.length / 2)._2 :: rSlice(xss)
    }
  }
  def lSlice(lst: List[List[Int]]): List[List[Int]]={
      lst match {
        case List() => Nil
        case xs::xss => xs.splitAt(xs.length/2)._1 ::lSlice(xss)
      }
    }
    (lSlice(lst),rSlice(lst))
  }

  // length -1 nao é preciso em todos porque é do quadrante a seguir
  def makeTree(bm: Bitmap): QTree[Coords] = {
    def aux(lst: List[List[Int]], p: Point): QTree[Coords] = {
      lst match {
        case Nil => QEmpty
        case List(List())=> QEmpty
        case List() => QEmpty
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
    aux(bm.bitmap.toList map (x => x.toList),(0,0))
  }

  //MAKE BITMAP
  def treeToImage(path: String,form:String, tree: QTree[Coords]): Unit ={
    val m =makeBitMap(tree)
    ImageUtil.writeImage(m.bitmap.array, path, form)
  }
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

  def makeBitMap(qTree:QTree[Coords]):Bitmap= {
    def aux(qTree:QTree[Coords]):List[List[Int]]= {
      qTree match {
        case QEmpty => Nil
        case QLeaf(s: Section) => leafToList(s)
        case QNode(_, l1, l2, l3, l4) => glue(aux(l1), aux(l2), aux(l3), aux(l4))
      }
    }

   Bitmap(aux(qTree).toArray map (x => x.toArray))
  }







}