package projeto

import projeto.Color.Color
import projeto.Coords.Coords
import projeto.Point.Point
import projeto.Section.Section

import scala.util.Random


case class Effects(myField:QTree[Coords]){
  def mirrorV ():QTree[Coords]= Effects.mirrorV(this.myField)
  def mirrorH ():QTree[Coords]= Effects.mirrorH(this.myField)
  def rotateR ():QTree[Coords]= Effects.rotateR(this.myField)
  def rotateL ():QTree[Coords]= Effects.rotateL(this.myField)
  def scale (doub: Double): QTree[Coords]= Effects.scale(this.myField, doub)
  def mapColorEffect(f:Color => Color):QTree[Coords] =Effects.mapColorEffect(f,this.myField)
  def noise (c:Color): Color=Effects.noise(c)
  def contrast (c:Color): Color=Effects.contrast(c)
  def sepia (c:Color): Color=Effects.sepia(c)

  def mapColorEffect_1(f:(Color, RandomWithState) => (Color,RandomWithState), r:RandomWithState):QTree[Coords]= Effects.mapColorEffect_1(f,this.myField,r)
  def noise_1 (c:Color, r:RandomWithState): (Color,RandomWithState)= Effects.noise_1(c,r)
}

object Effects {

  //MIRROR
  def changeCoords_Mirror(coords: Coords, length: Int): Coords = {
    val p1: Point = (length-coords._2._1, coords._1._2)
    val p2: Point = (length-coords._1._1, coords._2._2)
    (p1, p2)
  }
  def maximum(qt: QTree[Coords]): Int = qt match {
    case QEmpty => 0
    case QLeaf(s: Section) => s._1._2._2
    case QNode(v,l1, l2, l3, l4) => v._2._2.max(maximum(l1) max maximum(l2) max maximum(l3) max maximum (l4))
  }
  def mirrorV (qt:QTree[Coords]):QTree[Coords]= {
    val max =maximum(qt)
        qt match {
          case QEmpty => QEmpty
          case QLeaf(s: Section) => QLeaf(changeCoords_Mirror(s._1, max),s._2)
          case QNode(value, l1, l2, l3, l4) => QNode(changeCoords_Mirror(value, max), mirrorV(l2), mirrorV(l1), mirrorV(l4), mirrorV(l3))
        }
  }

  def mirrorH (qt:QTree[Coords]):QTree[Coords]={
    val max =maximum(qt)
    qt match {
      case QEmpty => QEmpty
      case QLeaf(s: Section) => QLeaf(changeCoords_Mirror(s._1, max),s._2)
      case QNode(value, l1, l2, l3, l4) => QNode(changeCoords_Mirror(value, max), mirrorH(l3), mirrorH(l4), mirrorH(l1), mirrorH(l2))
    }
  }

  //ROTATE
  def dimensions(qt: QTree[Coords]): (Int, Int) = {
    qt match {
      case QNode(value, _, _, _, _) => value._2
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

//SCALE
  def average(lst: List[Color]): Color = {
    val aux = (lst foldRight List(0,0,0)) ((x1, x2) => List(x1.head+x2.head, x1(1)+x2(1), x1(2)+x2(2)))
    List(aux.head/lst.length, aux(1)/lst.length, aux(2)/lst.length)
  }

  def gatherColors(qt1: QTree[Coords], qt2: QTree[Coords], qt3: QTree[Coords], qt4: QTree[Coords]): List[Color] = {
    def aux(qt: QTree[Coords]): List[Color] = {
      qt match {
        case QEmpty => List()
        case QLeaf(s: Section) => List(s._2)
        case QNode(_, l1, l2, l3, l4) => aux(l1):::aux(l2):::aux(l3):::aux(l4)
      }
    }
    aux(qt1):::aux(qt2):::aux(qt3):::aux(qt4)
  }

  def changeCoords_Scale(coords: Coords, scale: Double): Coords = {
    val p1: Point = (Math.round(coords._1._1 * scale).toInt, Math.round(coords._1._2 * scale).toInt)
    val p2: Point = (Math.round((coords._2._1 + 1) * scale - 1).toInt, Math.round((coords._2._2 + 1) * scale - 1).toInt)
    (p1, p2)
  }
  def scale(qt: QTree[Coords], doub: Double): QTree[Coords] = {
    qt match {
      case QEmpty => QEmpty
      case QLeaf(s: Section) => QLeaf(changeCoords_Scale(s._1, doub), s._2)
      case QNode(value, l1, l2, l3, l4) =>
        val newCoords = changeCoords_Scale(value, doub)
        if (newCoords._1._1 == newCoords._2._1 || newCoords._1._2 == newCoords._2._2) QLeaf(newCoords, average(gatherColors(l1, l2, l3, l4)))
        else
          QNode(changeCoords_Scale(value, doub), scale(l1, doub), scale(l2, doub), scale(l3, doub), scale(l4, doub))
    }
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
    val lum = ImageUtil.luminance(c.head, c(1), c(2))
    if (lum < 127) List(validatecomponent(c.head - 50), validatecomponent(c(1) - 50), validatecomponent(c(2) - 50))
    else List(validatecomponent(c.head + 50), validatecomponent(c(1) + 50), validatecomponent(c(2) + 50))
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

  //Noise puro
  def noise_1 (c:Color, r:RandomWithState): (Color,RandomWithState)={
    val random_noise= r.nextInt(122)

    (List (validatecomponent(random_noise._1+c.head),
      validatecomponent(random_noise._1+c(1)),
      validatecomponent(random_noise._1+c(2))),random_noise._2)


  }
  def mapColorEffect_1(f:(Color, RandomWithState) => (Color,RandomWithState), qt:QTree[Coords], r:RandomWithState):QTree[Coords]={
    qt match {
      case QEmpty=>QEmpty
      case QLeaf(s:Section) =>QLeaf((s._1,f(s._2,r)._1))
      case  QNode(a, l1, l2, l3, l4)=>QNode(a, mapColorEffect_1(f,l1,r), mapColorEffect_1(f,l2,r),
        mapColorEffect_1(f,l3,r), mapColorEffect_1(f,l4,r))
    }
  }


}



