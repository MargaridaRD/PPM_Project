package projeto

object Main {
  def main(args: Array[String]): Unit = {
    val tree:Tree = Tree(Bitmap(ImageUtil.readColorImage("src/projeto/img/rita.jpeg")))
   //println("x" + tree.makeTree())
    val teste_makeTree = tree.makeTree()
    val teste_makeBitMap = tree.makeBitMap(teste_makeTree).bitmap
    ImageUtil.writeImage(teste_makeBitMap, "src/projeto/img/teste_makeBitMap.png", "png")

    //////Efeitos////////////
    val effects:Effects = Effects(teste_makeTree)
    //mirrorV
    ImageUtil.writeImage(tree.makeBitMap(effects.mirrorV()).bitmap, "src/projeto/img/mirrorV.png", "png")

    //mirrorH
    ImageUtil.writeImage(tree.makeBitMap(effects.mirrorH()).bitmap, "src/projeto/img/mirrorH.png", "png")
    //rotateR
    ImageUtil.writeImage(tree.makeBitMap(effects.rotateR()).bitmap, "src/projeto/img/rotateR.png", "png")

    //rotateL
    ImageUtil.writeImage(tree.makeBitMap(effects.rotateL()).bitmap, "src/projeto/img/rotateL.png", "png")

    //mapColorEffect

    //sepia
    ImageUtil.writeImage(tree.makeBitMap(effects.mapColorEffect(effects.sepia)).bitmap, "src/projeto/img/sepia.png", "png")
    //noise
    ImageUtil.writeImage(tree.makeBitMap(effects.mapColorEffect(effects.noise)).bitmap, "src/projeto/img/noise.png", "png")
    //contrast
    ImageUtil.writeImage(tree.makeBitMap(effects.mapColorEffect(effects.contrast)).bitmap, "src/projeto/img/contrast.png", "png")
  // val r = newRandomState(10)
   //Bitmap( ImageUtil.writeImage(tree.makeBitMap(effects.mapColorEffect_1(effects.noise_1,r.nextInt(122)._2)), "src/projeto/img/noise1.png", "png"))

  }

}
