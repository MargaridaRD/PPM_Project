package projeto

import projeto.Color.Color
import projeto.Coords.Coords
import projeto.Section.Section

import scala.util.Random


case class Effects(myField:QTree[Coords]){
  def mirrorV ():QTree[Coords]= Effects.mirrorV(this.myField)
  def mirrorH ():QTree[Coords]= Effects.mirrorH(this.myField)
  def rotateR ():QTree[Coords]= Effects.rotateR(this.myField)
  def rotateL ():QTree[Coords]= Effects.rotateL(this.myField)
  def mapColorEffect(f:Color => Color):QTree[Coords] =Effects.mapColorEffect(f,this.myField)
  def noise (c:Color): Color=Effects.noise(c)
  def contrast (c:Color): Color=Effects.contrast(c)
  def sepia (c:Color): Color=Effects.sepia(c)
}

object Effects {

  //MIRROR
  def mirrorV (qt:QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty => QEmpty
      case QLeaf(s: Section) => QLeaf(s)
      case QNode(value, l1, l2, l3, l4) => QNode(value, mirrorV(l2), mirrorV(l1), mirrorV(l4), mirrorV(l3))
    }

  }

  def mirrorH (qt:QTree[Coords]):QTree[Coords]={
    qt match {
      case QEmpty => QEmpty
      case QLeaf(s: Section) => QLeaf(s)
      case QNode(value, l1, l2, l3, l4) => QNode(value, mirrorH(l3), mirrorH(l4), mirrorH(l1), mirrorH(l2))
    }
  }

  //ROTATE
  def dimensions(qt: QTree[Coords]): (Int, Int) = {
    qt match {
      case QNode(value, l1, l2, l3, l4) => (value._2)
    }
  }
  //rotate_R
  def rotateCoords_R(c: Coords, height: Int): Coords = {
    val swap =((c._1._1, c._2._2),(c._2._1, c._1._2)) //swap das coordenadas dos cantos
    ((height - swap._1._2, swap._1._1), (height - swap._2._2, swap._2._1))
  }
  def rotateR(qt:QTree[Coords]): QTree[Coords]={
    def aux(qt_aux: QTree[Coords], height: Int): QTree[Coords] = {
      qt_aux match {


        case QEmpty => QEmpty
        case QLeaf(s: Section) => QLeaf(rotateCoords_R(s._1, height), s._2)
        case QNode(value, l1, l2, l3, l4) => QNode(rotateCoords_R(value, height), aux(l3, height), aux(l1, height), aux(l4, height), aux(l2, height))
      }
    }
    aux(qt, dimensions(qt)._2)
  }
  //rotate_L
  def rotateCoords_L(c: Coords, width: Int): Coords = {
    val swap =((c._2._1, c._1._2),(c._1._1, c._2._2)) //swap das coordenadas dos cantos
    ((swap._1._2, width - swap._1._1), ( swap._2._2, width - swap._2._1))
  }


  def rotateL(qt:QTree[Coords]): QTree[Coords]={
    def aux(qt_aux: QTree[Coords], height: Int): QTree[Coords] = {
      qt_aux match {
        case QEmpty => QEmpty
        case QLeaf(s: Section) => QLeaf(rotateCoords_L(s._1, height), s._2)
        case QNode(value, l1, l2, l3, l4) => QNode(rotateCoords_L(value, height), aux(l2, height), aux(l4, height), aux(l1, height), aux(l3, height))
      }
    }
    aux(qt, dimensions(qt)._2)
  }


  //EFFECTS
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
    List( 255-c.head, 255-c(1), 255-c(2))
  }

  //Noise tem valores Random de 0 e 122
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

}



