package projeto

import projeto.Gallery.Album


case class Gallery(gallery:Album){

}

object Gallery{
   type Image= (Int, Bitmap)
   type Album = List[Image]



  def insert(bm:Bitmap, g:Album) : Album={
    g match{
      case Nil => List((g.length-1,bm))
      case x::xs => x::xs:::List(g.length-1,bm)
    }
  }

   def shearchImage(img:Image, g:Gallery):Option[Image] = {
    g.gallery find (x=> x._1== img._1)
  }



}
