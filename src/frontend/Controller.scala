package frontend

import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, MenuItem, TextField}
import javafx.scene.image.{Image, ImageView}
import projeto.Gallery



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
  }

  def valueScale():Unit={
    scaleText.setVisible(true)
  }
  def valueOfScale():Unit={

  }
  def effectMirrorV():Unit={
    println("mirrorV")
  }
  def effectMirrorH():Unit={
    println("mirrirH")

  }
  def effectRotateR():Unit={
    println("RotateR")

  }
  def effectRotateL():Unit={
    println("RotateL")

  }
  def effectContrast():Unit={
    println("contrast")

  }
  def effectSepia():Unit={
    println("sepia")

  }
  def effectNoise():Unit={
    println("noise")

  }
  def delete():Unit={
    println("delete")

  }
  def guardar():Unit={
    println("guardar")

  }
  def cancelar():Unit={
    println("cancelar")

  }
  def contrast():Unit={
    println("contrast")
  }

  def next():Unit={
       if( !FxApp1.album.isEmpty) {
         val img: (Int, String) = Gallery(FxApp1.album).next(id.getText.toInt)
         setMainImage(img._2, img._1)
       }
  }
  def previous():Unit={
    if( !FxApp1.album.isEmpty) {
      val img: (Int, String) = Gallery(FxApp1.album).previous(id.getText.toInt)
      setMainImage( img._2, img._1)
    }

  }

  def onClickAdd ():Unit={

  }

  def onClickMirrorV ():Unit={

  }


}
