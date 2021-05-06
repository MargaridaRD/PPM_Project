package frontend

import javafx.fxml.FXML

import javafx.scene.control.{Button, MenuItem}
import javafx.scene.image.ImageView



class Controller {

  @FXML
  private var imageView: ImageView =
  @FXML
  private var setaR: ImageView =
  @FXML
  private var setaL: ImageView =
  @FXML
  private var buttonAdd: Button =
  @FXML
  private var buttonScale: Button =
  @FXML
  private var buttonMirrorV: Button =
  @FXML
  private var buttonMirrorH: Button =
  @FXML
  private var buttonRotateR: Button =
  @FXML
  private var buttonRotateL: Button =
  @FXML
  private var menuItemContrast: MenuItem =
  @FXML
  private var menuItemSepia: MenuItem =
  @FXML
  private var menuItemNoise: MenuItem =
  @FXML
  private var buttonCancelar: Button =
  @FXML
  private var buttonGuardar: Button =



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
  def gurdar():Unit={
    println("guardar")

  }
  def cancelar():Unit={
    println("cancelar")

  }
  def seta_L():Unit={
    println("cancelar")

  }

  def seta_R():Unit={
    println("cancelar")

  }

  def contrast():Unit={
    println("contrst")
  }


  def getTile(): TilePane = {
    tile
  }

  def getScroll(): ScrollPane = {
    scroll
  }

}
