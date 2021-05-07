package frontend

import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, TextField}
import javafx.scene.image.{Image, ImageView}
import projeto.{Effects, Gallery, Tree}

import java.io.File
import java.nio.file.{Files, Paths, StandardCopyOption}
import scala.util.Try






class Controller {


  @FXML
  private var name: TextField =_
  @FXML
  private var id: Label =_
  @FXML
  private var imageView: ImageView =_
  @FXML
  private var pathText: TextField =_
  @FXML
  private var buttonAddOk: Button =_
  @FXML
  private var buttonScaleOk: Button =_
  @FXML
  private var scaleText: TextField =_
  @FXML
  private var buttonNameOK: Button =_


  def changeName ():Unit={
    buttonNameOK.setVisible(true)
  }

  def changeNameOk ():Unit={
    buttonNameOK.setVisible(false)
    if (name.getText.nonEmpty) {
      new File("out/production/PPM_Project/projeto/img/" + name.getPromptText).renameTo(new File("out/production/PPM_Project/projeto/img/" + name.getText))
      val index1 = FxApp1.album.indexWhere(x => { x._1 == id.getText.toInt } )
      name.setPromptText(name.getText)
      FxApp1.album = FxApp1.album.updated(index1,(id.getText.toInt,name.getPromptText))
      name.setText("")
    }
  }

  def addImage(): Unit ={
    if( FxApp1.isEdited) save()
    pathText.setVisible(true)
    buttonAddOk.setVisible(true)
  }

  def addImageOK():Unit={
    if (!pathText.getText.equals("Insira o nome da imagem:")) {
      pathText.setVisible(false)
      buttonAddOk.setVisible(false)
      Try {
        setMainImage(pathText.getText,FxApp1.album.length)
          FxApp1.album = new Gallery(FxApp1.album).insert(pathText.getText)
      }
      pathText.setText("")
    }
    println("dps do add" + FxApp1.album)

  }

  def setMainImage(s: String,i:Int): Unit = {
    imageView.setImage(new Image("/projeto/img/"+s))
    name.setPromptText(s)
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
    if (FxApp1.album.nonEmpty) {
      FxApp1.album = new Gallery(FxApp1.album).delete(id.getText.toInt)
      if (FxApp1.album.nonEmpty)
        next()
      else {
        imageView.setImage(null)
        name.setPromptText("")
      }
    }
  }


  def save():Unit={
    if(FxApp1.isEdited) {
      Files.move(Paths.get("out/production/PPM_Project/temp.png"),
        Paths.get("out/production/PPM_Project/projeto/img/" + name.getPromptText),
        StandardCopyOption.REPLACE_EXISTING)
    }
    FxApp1.isEdited=false

  }
  def cancel():Unit={
    imageView.setImage(new Image("/projeto/img/"+ name.getPromptText))
    FxApp1.isEdited=false

  }

  def next():Unit={
    if( FxApp1.isEdited) save()
       if(FxApp1.album.nonEmpty) {
         val img: (Int, String) = Gallery(FxApp1.album).next(id.getText.toInt)
         setMainImage(img._2, img._1)
       }

  }
  def previous():Unit={
    if( FxApp1.isEdited) save()
    if(FxApp1.album.nonEmpty) {
      val img: (Int, String) = Gallery(FxApp1.album).previous(id.getText.toInt)
      setMainImage( img._2, img._1)
    }
  }

  def rightFile () : Tree= {
    if (FxApp1.isEdited) {
      Tree("out/production/PPM_Project/temp.png")
    } else {
      Tree("out/production/PPM_Project/projeto/img/" + name.getPromptText())
    }
  }
  





  }
