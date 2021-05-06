package frontend

import javafx.fxml.FXML
import javafx.scene.control.{Button, MenuItem}
import javafx.scene.image.{Image, ImageView}



class Controller {

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

  def setMainImage(s: String): Unit = {
    imageView.setImage(new Image(s))
  }

  def effectScale():Unit={
    println("scale")


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

    println(imageView.getImage)
  }
  def previous():Unit={
    println("previous")
  }


}
