package frontend

import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, MenuItem, TextArea, TextField}
import javafx.scene.image.{Image, ImageView}
import projeto.{Effects, Gallery, Tree}




class Controller {


  @FXML
  private var name: Label =_
  @FXML
  private var id: Label =_
  @FXML
  private var imageView: ImageView =_
  @FXML
  private var buttonAdd: Button =_
  @FXML
  private var buttonScale: Button =_
  @FXML
  private var buttonMirrorV: Button =_
  @FXML
  private var buttonMirrorH: Button =_
  @FXML
  private var buttonRotateR: Button =_
  @FXML
  private var buttonRotateL: Button =_
  @FXML
  private var menuItemContrast: MenuItem =_
  @FXML
  private var menuItemSepia: MenuItem =_
  @FXML
  private var menuItemNoise: MenuItem =_
  @FXML
  private var buttonDelete: Button =_
  @FXML
  private var buttonCancelar: Button =_
  @FXML
  private var buttonGuardar: Button =_
  @FXML
  private var buttonNext: Button =_
  @FXML
  private var buttonPrevious: Button =_
  @FXML
  private var pathText: TextField =_
  @FXML
  private var scaleText: TextField =_



  def setMainImage(s: String,i:Int): Unit = { //mete a imagem na ImageView
    imageView.setImage(new Image(s))
    name.setText(s)
    id.setText(i.toString)
    FxApp1.isEdited=false
  }

  def valueScale():Unit={
    scaleText.setVisible(true)
  }

  def valueOfScale():Unit={

  }



  def onClickRotateR():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "src/projeto/img/~temp.png", "png",Effects(tree.imageToTree()).rotateR())
    imageView.setImage(new  Image("projeto/img/~temp.png"))
    FxApp1.isEdited=true

  }
  def onClickRotateL():Unit={
     val tree:Tree= rightFile
     tree.treeToImage( "src/projeto/img/~temp.png", "png",Effects(tree.imageToTree()).rotateL())
     imageView.setImage(new  Image("projeto/img/~temp.png"))
     FxApp1.isEdited=true

  }

  def onClickSepia():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "src/projeto/img/~temp.png", "png",Effects(tree.imageToTree()).mapColorEffect(Effects.sepia) )
    imageView.setImage(new  Image("projeto/img/~temp.png"))
    FxApp1.isEdited=true

  }
  def onClickNoise():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "src/projeto/img/~temp.png", "png",Effects(tree.imageToTree()).mapColorEffect(Effects.noise))
    imageView.setImage(new  Image("projeto/img/~temp.png"))
    FxApp1.isEdited=true

  }
  def onClickContrast():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "src/projeto/img/~temp.png", "png",Effects(tree.imageToTree()).mapColorEffect(Effects.contrast))
    imageView.setImage(new  Image("projeto/img/~temp.png"))
    FxApp1.isEdited=true
  }
  def delete():Unit={
    println("delete")

  }
  def guardar():Unit={
    FxApp1.isEdited=false

  }
  def cancelar():Unit={
    FxApp1.isEdited=false

  }


  def next():Unit={
       if(FxApp1.album.nonEmpty) {
         val img: (Int, String) = Gallery(FxApp1.album).next(id.getText.toInt)
         setMainImage(img._2, img._1)


       }
  }
  def previous():Unit={
    if(FxApp1.album.nonEmpty) {
      val img: (Int, String) = Gallery(FxApp1.album).previous(id.getText.toInt)
      setMainImage( img._2, img._1)

    }

  }

  def onClickAdd ():Unit={
    println("ola do add")
    FxApp1.isEdited=false
  }


  def onClickMirrorH():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "src/projeto/img/~temp.png", "png",Effects(tree.imageToTree()).mirrorV())
    imageView.setImage(new  Image("projeto/img/~temp.png"))
    FxApp1.isEdited=true
  }
  def onClickMirrorV ():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "src/projeto/img/~temp.png", "png",Effects(tree.imageToTree()).mirrorV())
    imageView.setImage(new  Image("projeto/img/~temp.png"))
    FxApp1.isEdited=true
  }
  def rightFile () : Tree= {
    if (FxApp1.isEdited) {
      println("temp")
      Tree("src/projeto/img/~temp.png")
    } else {
      println("NOTtemp")
      Tree("src/" + name.getText())
    }
  }



}
