package projeto

import projeto.Color.Color
import projeto.Coords.Coords
import projeto.Point.Point
import projeto.Section.Section

import scala.annotation.tailrec
import scala.util.Random


case class Tree(myField: QTree[Coords]) {

}

object Tree{

  //MAKE TREE
  //verifica se todos os pixeis são iguais para um quadrante
  /*
  @tailrec
  def verify_pixels(lst:List[List[Int]]):Boolean={
      lst match {
        case Nil => true
        case List(x) =>  if ( x.forall(_ == x.head) ) true else false
        case y :: ys :: yss => if ( y.forall(_ == y.head) && y == ys) verify_pixels(ys::yss) else false
      }
  }*/
  def verify_pixels(lst:List[List[Int]]):Boolean={
    @tailrec
    def aux(l:List[Int]):Boolean = {
      l match {
        case Nil => true
        case List()=> true
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

      case xs::xss =>{
        val x = verticalSlice(xss)
        (xs.splitAt(xs.length/2)._1 :: x._1, xs.splitAt(xs.length/2)._2 :: xzz._2)
      }
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
            val ver =verticalSlice(lst)
            val hor1 = horizontalSlice(ver._1)
            val hor2 = horizontalSlice(ver._2)
            QNode((p, (p._1 + lst.head.length - 1, p._2 + lst.length - 1)),
              aux(hor1._1, p),
              aux(hor2._1, (p._1 + lst.head.length / 2, p._2)),
              aux(hor1._2, (p._1, p._2 + lst.length / 2)),
              aux(hor2._2, (p._1 + lst.head.length / 2, p._2 + lst.length / 2)))
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

  //MIRROR

  def mirrorV (qt:QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty => QEmpty
      case QLeaf(s: Section) => QLeaf(s)
      case QNode(value, l1, l2, l3, l4) => QNode(value, mirrorV(l2), mirrorV(l1), mirrorV(l4), mirrorV(l3))
    }

  }

  def mirrorH (qt:QTree[Coords]):QTree[Coords]={
    val x=2
    val y=3
    qt match {

      case QEmpty => QEmpty
      //case QLeaf(s: Section) => QLeaf((point_1_x,poit_1_y),(point_2_x,point_2_y),s._2)
      case QLeaf(s: Section) => QLeaf((s._1._1,s._1._1),(s._1._2,s._1._2),s._2)
      case QNode(value, l1, l2, l3, l4) => QNode(value, mirrorH(l3), mirrorH(l4), mirrorH(l1), mirrorH(l2))
    }
  }

  def rotateR (qt:QTree[Coords]):QTree[Coords]={
    qt match {

      case QEmpty => QEmpty
      case QLeaf(s: Section) => QLeaf()
      case QNode(value, l1, l2, l3, l4) => QNode((value._1, (value._2._2,value._2._1)), rotateL(l2), rotateL(l4), rotateL(l1), rotateL(l3))
    }
  }


  def rotateL (qt:QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty => QEmpty
      case QLeaf(s: Section) => QLeaf()
      case QNode(value, l1, l2, l3, l4) => QNode((value._1, (value._2._2,value._2._1)), rotateL(l2), rotateL(l4), rotateL(l1), rotateL(l3))
    }
  }

  //Color

  def validatecomponent(i:Int): Int={
    i match {
      case i if i < 0 => 0
      case i if i > 255 => 255
      case _ => i
    }
  }

  //depth and intensity values (ideally 20, and 30)
  def sepia (c:Color): Color={
    val depth = 20
    val intensity = 30
    val avg= (c.head +c(1) +c(2))/3
    List (validatecomponent(avg + depth*2),validatecomponent(avg+ depth), validatecomponent(avg-intensity))

  }
  def contrast (c:Color): Color={
    // val lum =ImageUtil.luminance(c.head,c(1),c(2))
    //usando luminance
    //List (validatecomponent(c.head +lum),validatecomponent(c(1) +lum), validatecomponent(c(2) +lum))
    // List (validatecomponent(c.head -lum),validatecomponent(c(1) -lum), validatecomponent(c(2) -lum))

    //255 - a cor
    List( 255-c.head, 255-c(1), 255-c(2))

  }
  //Noise tem valores Random de 0 e 122 (Ainda em Discussão) é suposto mudar a cor de cada pixel ou de cada QLeaf

  def noise (c:Color): Color={
    val random_noise= Random.nextInt(122)

    List (validatecomponent(random_noise+c.head),validatecomponent(random_noise+c(1)), validatecomponent(random_noise+c(2)))

  }


  def mapColorEffect(f:Color => Color, qt:QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty=>QEmpty
      case QLeaf(s:Section) =>QLeaf((s._1,f(s._2)))
      case  QNode(a, l1, l2, l3, l4)=>QNode(a, mapColorEffect(f,l1), mapColorEffect(f,l2), mapColorEffect(f,l3), mapColorEffect(f,l4))
    }

  }




  def main(args: Array[String]): Unit = {
    val teste = makeTree(ImageUtil.readColorImage("src/projeto/img/400.png"))
    println("teste" + teste)
    ImageUtil.writeImage(makeBitMap(teste), "src/projeto/img/teste_400.png", "png")

    //val mirror_H = mirrorH(teste)
    //ImageUtil.writeImage(makeBitMap(mirror_H), "src/projeto/img/t1.png", "png")

    //val mirror_V = mirrorH(mirrorV(teste))
    //ImageUtil.writeImage(makeBitMap(mirror_V), "src/projeto/img/t2.png", "png")

    //val teste_sepia= mapColorEffect(sepia, teste)
    //ImageUtil.writeImage(makeBitMap(teste_sepia), "src/projeto/img/teste_sepia.png", "png")

    //val teste_contrast= mapColorEffect(contrast, teste)
    //ImageUtil.writeImage(makeBitMap(teste_contrast), "src/projeto/img/teste_contrast.png", "png")

    //val teste_noise= mapColorEffect(noise, teste)
    //ImageUtil.writeImage(makeBitMap(teste_noise), "src/projeto/img/teste_noise.png", "png")

    //  val rotate_R = rotateR(teste)
    // ImageUtil.writeImage(makeBitMap(rotate_R), "src/projeto/img/rotate_R.png", "png")

    //val rotate_L = rotateL(teste)
    //ImageUtil.writeImage(makeBitMap(rotate_L), "src/projeto/img/rotate_L.png", "png")


  }



}