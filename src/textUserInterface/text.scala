package textUserInterface

import projeto.Effects
import projeto.Gallery.Album
import projeto.Tree

import java.util
import java.util.Scanner


class text (){
  def menu(album1: util.ArrayList[String]): Unit= text.menu(album1)

}

object text{
  def menu(album1: util.ArrayList[String]): Unit = {

    System.out.println("Album: " + album1)
    System.out.println("Escolha uma imagem")
    val sc = new Scanner(System.in)
    var x = sc.nextLine
    while ( {
      !(album1 contains x)
    }) {
      System.out.println("Imagem não encontrada. Insira nova imagem ")
      x = sc.nextLine
    }
    System.out.print("Escolha um efeito:\n" + "1-mirrorV\n" + "2-mirrorH\n" + "3-rotateR\n" + "4-rotateL\n" + "5-scale\n" + "6-noise\n" + "7-contrast\n" + "8-sepia\n" + "0-sair\n")
    var option = sc.nextInt
    while ( {
      option != 0
    }) {
      val img = new Tree("src/projeto/img/" + x)
      val effects = new Effects(img.imageToTree)
      option match {
        case 1 =>
          img.treeToImage("src/projeto/img/" + x, "png", effects.mirrorV)

        case 2 =>
          img.treeToImage("src/projeto/img/" + x, "png", effects.mirrorH)

        case 3 =>
          img.treeToImage("src/projeto/img/" + x, "png", effects.rotateR)

        case 4 =>
          img.treeToImage("src/projeto/img/" + x, "png", effects.rotateL)

        case 5 =>
          System.out.println("Scale de quanto?? ")
          val scale_value = sc.nextDouble
          img.treeToImage("src/projeto/img/" + x, "png", effects.scale(scale_value))

        case 6 =>
          img.treeToImage("src/projeto/img/" + x, "png", effects.mapColorEffect(effects.contrast))

        case 7 =>
          img.treeToImage("src/projeto/img/" + x, "png", effects.mapColorEffect(effects.sepia))

        case 8 =>
          img.treeToImage("src/projeto/img/" + x, "png", effects.mapColorEffect(effects.noise))

        case _ =>
          System.out.println("Insira um númro válido ")
          option = sc.nextInt

      }
      System.out.println("Insira outro efeito ")
      option = sc.nextInt
    }
    sc.close()
  }

}

