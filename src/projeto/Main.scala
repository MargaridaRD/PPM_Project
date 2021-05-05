package projeto


object Main {
  def main(args: Array[String]): Unit = {
    val tree:Tree= Tree("src/projeto/img/rita.jpeg")
    val teste_makeTree = tree.imageToTree()
//    tree.treeToImage("src/projeto/img/teste.png","png",teste_makeTree)

//    //////Efeitos////////////
    val effects:Effects = Effects(teste_makeTree)
//    //mirrorV
//    tree.treeToImage( "src/projeto/img/mirrorV.png", "png",effects.mirrorV())
//    //mirrorH
//    tree.treeToImage( "src/projeto/img/mirrorH.png", "png",effects.mirrorH())
//    //rotateR
//    tree.treeToImage( "src/projeto/img/rotateR.png", "png",effects.rotateR())
//    //rotateL
//    tree.treeToImage( "src/projeto/img/rotateR.png", "png",effects.rotateL())
//    //scale
//    tree.treeToImage( "src/projeto/img/scale.png", "png",effects.scale(0.5))
//    //sepia
   tree.treeToImage( "src/projeto/img/sepia.png", "png",effects.mapColorEffect(effects.sepia))
//    //noise
//    tree.treeToImage( "src/projeto/img/noise.png", "png",effects.mapColorEffect(effects.noise))
    //    //contrast
        //tree.treeToImage( "src/projeto/img/contrast.png", "png",effects.mapColorEffect(effects.contrast))

    //noise_puro
   // val r = MyRandom(11)
   // tree.treeToImage( "src/projeto/img/noise.png", "png",effects.mapColorEffect_1(effects.noise_1,r ))



    ///////Gallery//////////////////////////////////////////////////////////
    val gallery2:Gallery= Gallery (List())
    val gallery1 = Gallery (List((0,"src/projeto/img/rita.jpeg"),(1,"src/projeto/img/objc2_2.png"),(2,"src/projeto/img/objc2_3.png")))

    //insert
   val insert1:Gallery=  Gallery(gallery1.insert("src/projeto/img/4cores15_10.png"))
  println("insert: " + insert1)

    //delete
    //val delete1=  Gallery(insert1.delete(0))
    //println("delete1: " + delete1)

    //searchImage
    //val searchImage1=  insert1.searchImage(2)
    //println("searchImage1: " + searchImage1)

      // swapImage n
   // val swapImage1:Gallery=  Gallery(gallery2.swapImage(0,3))
   // println("swapImage1: " + swapImage1)

    //previous
    //val scroll1= gallery1.scroll(Gallery.previous,1)
    //println("scroll1: " + scroll1)

    //next
    //val scroll2= gallery1.scroll(Gallery.next,1)
    //println("scroll2: " + scroll2)
  }

}
