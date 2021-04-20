package projeto

object Main {
  def main(args: Array[String]): Unit = {
    val tree:Tree= Tree(ImageUtil.readColorImage("src/projeto/img/169x300.png"))
    val teste_makeTree = tree.makeTree()
    val teste_makeBitMap = tree.makeBitMap(teste_makeTree)
    ImageUtil.writeImage(teste_makeBitMap, "src/projeto/img/teste_makeBitMap.png", "png")

    //////Efeitos////////////
    //val effects:Effects = Effects(teste_makeTree)
    //mirrorV
    //ImageUtil.writeImage(tree.makeBitMap(effects.mirrorV()), "src/projeto/img/mirrorV.png", "png")

    //mirrorH
    //ImageUtil.writeImage(tree.makeBitMap(effects.mirrorH()), "src/projeto/img/mirrorH.png", "png")
    //rotateR
    //ImageUtil.writeImage(tree.makeBitMap(effects.rotateR()), "src/projeto/img/rotateR.png", "png")

    //rotateL
    //ImageUtil.writeImage(tree.makeBitMap(effects.rotateL()), "src/projeto/img/rotateL.png", "png")

    //mapColorEffect

    //sepia
   // ImageUtil.writeImage(tree.makeBitMap(effects.mapColorEffect(effects.sepia)), "src/projeto/img/sepia.png", "png")
    //noise
   // ImageUtil.writeImage(tree.makeBitMap(effects.mapColorEffect(effects.noise)), "src/projeto/img/noise.png", "png")
    //contrast
   // ImageUtil.writeImage(tree.makeBitMap(effects.mapColorEffect(effects.contrast)), "src/projeto/img/contrast.png", "png")

  }

}
