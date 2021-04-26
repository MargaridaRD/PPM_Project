package projeto

object Main {
  def main(args: Array[String]): Unit = {
    val tree:Tree= Tree("src/projeto/img/rita.jpeg")
    val teste_makeTree = tree.imageToTree()
//    tree.treeToImage("src/projeto/img/teste.png","png",teste_makeTree)

    //////Efeitos////////////
    val effects:Effects = Effects(teste_makeTree)
    //mirrorV
    tree.treeToImage( "src/projeto/img/mirrorV.png", "png",effects.mirrorV())
    //mirrorH
    tree.treeToImage( "src/projeto/img/mirrorH.png", "png",effects.mirrorH())
    //rotateR
    tree.treeToImage( "src/projeto/img/rotateR.png", "png",effects.rotateR())
    //rotateL
    tree.treeToImage( "src/projeto/img/rotateR.png", "png",effects.rotateL())
    //scale
    tree.treeToImage( "src/projeto/img/scale.png", "png",effects.scale(0.5))
    //sepia
    tree.treeToImage( "src/projeto/img/sepia.png", "png",effects.mapColorEffect(effects.sepia))
    //noise
    tree.treeToImage( "src/projeto/img/noise.png", "png",effects.mapColorEffect(effects.noise))
    //contrast
    tree.treeToImage( "src/projeto/img/contrast.png", "png",effects.mapColorEffect(effects.contrast))

  }

}
