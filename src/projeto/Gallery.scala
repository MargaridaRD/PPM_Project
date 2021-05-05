package projeto


import projeto.Gallery.{Album, Image}

import scala.util.{Failure, Success, Try}


case class Gallery(album:Album){
  def insert(bm:String) : Album = Gallery.insert(bm,this.album)
  def searchImage(id:Int):Option[Image] = Gallery.searchImage(id, this.album)
  def swapImage(id1:Int, id2:Int ): Album = Gallery.swapImage(id1,id2,this.album)
  def delete (id:Int):Album = Gallery.delete(id,this.album)
  def next( id:Int): Image= Gallery.next(id,this.album)
  def previous( id:Int): Image= Gallery.previous(id,this.album)
  def scroll ( f: (Int, Album) => Image , id:Int)= Gallery.scroll(f,id,this.album)
}

object Gallery{
  //ver se dá para string
   type Image= (Int, String)
   type Album = List[Image]


//verificar album
  def insert(bm:String, album: Album) : Album={
      album:+(album.length,bm)
  }

   def searchImage(id:Int, album: Album):Option[Image] = {
    album find (x=> x._1 == id)
  }


//não mudamos os ids das outras, ver com img== None
  def delete (id:Int, album:Album):Album={
    val img = searchImage(id,album)
    album match {
        case Nil => album
        case x :: xs => if (x._1 == id || img.isEmpty) xs else x :: delete(id, album)
      }
  }

  def swapImage(id1:Int, id2:Int, album: Album ): Album = {
    val index1 = album.indexWhere(x => { x._1 == id1 } )
    val index2 = album.indexWhere(x => { x._1 == id2 } )
    if(index1 != -1 && index2 != -1)
    {
      val img1 = album.apply(index1)
      val img2 = album.apply(index2)

      val album1 = album.updated(index1,img2)
      album1.updated(index2,img1)
    }
    else album
  }
//Verificar quando  o album é vazio
  // sew for a ultima imagem devolve a primeira
  def next( id:Int, album: Album): Image={
    val index = album.indexWhere(x => { x._1 == id } )
    if(index != -1 && index < album.length-1) album.apply(index +1)
    else album.apply(0)
  }
  def previous ( id:Int, album: Album):Image={
    val index = album.indexWhere(x => { x._1 == id } )
    if(index != -1 && index >0) album.apply(index -1)
    else album.apply(album.length-1)
  }

  def scroll ( f: (Int, Album) => Image , id:Int, album:Album)  =
      Try(f(id, album)) match {
      case Success( img) =>img
      case Failure(e) => println("Não há imagens na Galeria")
    }






}
