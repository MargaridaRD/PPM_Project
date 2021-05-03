package projeto

import projeto.Gallery.Album


case class Gallery(gallery:Album){

}

object Gallery{
  type Image= (Int, Bitmap)
  type Album = List[Image]


  //verificar album
  def insert(bm:Bitmap, album: Album) : Album={
    album:+(album.length,bm)
  }
  /*
  def insertOrd(img:Image,album: Album) : Album = {

    album match{
      case Nil => List(img)
      case x::xs => if( img._1 < x._1) img::x::xs else x::insertOrd(img, xs)
    }
  }*/

  def searchImage(id:Int, album: Album):Option[Image] = {
    album find (x=> x._1 == id)
  }



  def swapImage(id1:Int, id2:Int, album: Album ): Album = {
    val index1 = album.indexWhere(x => { x == id1 } )
    val index2 = album.indexWhere(x => { x == id2 } )
    if(index1 != -1 && index2 != -1)
    {
      val img1 = album.apply(index1)
      val img2 = album.apply(index2)

      val album1 =  album.updated(index1,img2)
      album1.updated(index2,img1)

    }
    else album
  }

  def delete (id:Int, album:Album):Album={
    val img = searchImage(id,album)
    album match {
      case
    }
  }



}
