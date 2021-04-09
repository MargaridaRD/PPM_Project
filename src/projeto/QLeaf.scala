package projeto

case class QLeaf[A, B](value: B) extends QTree[A]

// QLeaf[Coords, Section] = QLeaf((((0,0):Point,(0,0):Point):Coords, Color.red):Section)

