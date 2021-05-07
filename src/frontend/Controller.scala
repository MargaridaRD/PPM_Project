package frontend

import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, MenuItem, TextArea, TextField}
import javafx.scene.image.{Image, ImageView}
import projeto.{Effects, Gallery, Tree}

import java.io.File
import java.nio.file.Files
import scala.util.Try




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
  private var buttonSave: Button =_
  @FXML
  private var buttonNext: Button =_
  @FXML
  private var buttonPrevious: Button =_
  @FXML
  private var pathText: TextField =_
  @FXML
  private var buttonAddOk: Button =_
  @FXML
  private var buttonScaleOk: Button =_
  @FXML
  private var scaleText: TextField =_




  def addImage(): Unit ={
    pathText.setVisible(true)
    buttonAddOk.setVisible(true)
  }
  def addImageOK():Unit={
    if (!pathText.getText.equals("Insira o nome da imagem:")) {
      pathText.setVisible(false)
      buttonAddOk.setVisible(false)
      FxApp1.album = new Gallery(FxApp1.album).insert(pathText.getText)
      setMainImage(pathText.getText,FxApp1.album.length-1)
      pathText.setText("")
    }
  }
  def setMainImage(s: String,i:Int): Unit = { //mete a imagem na ImageView
    imageView.setImage(new Image("/projeto/img/"+s))
    name.setText(s)
    id.setText(i.toString)
    FxApp1.isEdited=false
  }

  def valueScale():Unit={
    scaleText.setVisible(true)
    buttonScaleOk.setVisible(true)
  }
  def valueOfScale():Unit= {
    if (scaleText.getText.nonEmpty) {
      scaleText.setVisible(false)
      buttonScaleOk.setVisible(false)
      val value: Double = scaleText.getText.toDouble
      scaleText.setText("")
      val tree: Tree = rightFile
      tree.treeToImage("out/production/PPM_Project/temp.png", "png", Effects(tree.imageToTree()).scale(value))
      imageView.setImage(new Image("temp.png"))
    }
  }

  def onClickRotateR():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "out/production/PPM_Project/temp.png", "png",Effects(tree.imageToTree()).rotateR())
    imageView.setImage(new  Image("temp.png"))
    FxApp1.isEdited=true

  }
  def onClickRotateL():Unit={
     val tree:Tree= rightFile
     tree.treeToImage( "out/production/PPM_Project/temp.png", "png",Effects(tree.imageToTree()).rotateL())
    imageView.setImage(new  Image("temp.png"))
     FxApp1.isEdited=true

  }

  def onClickMirrorH():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "out/production/PPM_Project/temp.png", "png",Effects(tree.imageToTree()).mirrorH())
    imageView.setImage(new  Image("temp.png"))
    FxApp1.isEdited=true
  }
  def onClickMirrorV ():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "out/production/PPM_Project/temp.png", "png",Effects(tree.imageToTree()).mirrorV())
    imageView.setImage(new  Image("temp.png"))
    FxApp1.isEdited=true
  }

  def onClickSepia():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "out/production/PPM_Project/temp.png", "png",Effects(tree.imageToTree()).mapColorEffect(Effects.sepia) )
    imageView.setImage(new  Image("temp.png"))
    FxApp1.isEdited=true

  }
  def onClickNoise():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "out/production/PPM_Project/temp.png", "png",Effects(tree.imageToTree()).mapColorEffect(Effects.noise))
    imageView.setImage(new  Image("temp.png"))
    FxApp1.isEdited=true

  }
  def onClickContrast():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "out/production/PPM_Project/temp.png", "png",Effects(tree.imageToTree()).mapColorEffect(Effects.contrast))
    imageView.setImage(new  Image("temp.png"))
    FxApp1.isEdited=true
  }

  def delete():Unit={
    if (!FxApp1.album.isEmpty) {
      FxApp1.album = new Gallery(Gallery(FxApp1.album).delete(id.getText.toInt)).album
      if (!FxApp1.album.isEmpty)
        previous()
      else {
        imageView.setImage(null)
        name.setText("")

      }

    }
  }


  def save():Unit={
    if(FxApp1.isEdited) {
      Files.copy(new File("temp.png").toPath,new File("out/production/PPM_Project/projeto/img"+name.getText).toPath)
    }


  }
  def cancelar():Unit={
    imageView.setImage(new Image(name.getText))
    val f:File= new File("src/projeto/img/temp.png")
    if (f.delete()) Some(f) else None
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



  def rightFile () : Tree= {
    if (FxApp1.isEdited) {
      Tree("out/production/PPM_Project/temp.png")
    } else {
      Tree("out/production/PPM_Project/projeto/img/" + name.getText())
    }
  }
  





  }
