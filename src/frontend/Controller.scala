package frontend


import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, MenuItem, TextField}
import javafx.scene.image.{Image, ImageView}
import projeto.{Effects, Gallery, Tree}
import java.io.File





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

    //Isto é alogica da coisa agora fazer quando da enter
    if(scaleText.getText.nonEmpty) {
      val value: Double = scaleText.getText.toDouble // vais buscar o valor inserido
      println(value)
      scaleText.setVisible(false)
      val tree: Tree = rightFile
      tree.treeToImage("src/projeto/img/temp.png", "png", Effects(tree.imageToTree()).scale(value))
      imageView.setImage(new Image("projeto/img/temp.png"))
    }

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
  def delete():Unit= {
    if (!FxApp1.album.isEmpty) {
      FxApp1.album = new Gallery(Gallery(FxApp1.album).delete(id.getText.toInt)).album
      if (!FxApp1.album.isEmpty) {
        previous()
      }
      else {
        imageView.setImage(null)
      }
    }
  }
    //apagar do ficheiro album.txt => falta Isso no final quabdo fecha
  def guardar():Unit={
    println("guardar")

  }
  def cancelar():Unit={
   imageView.setImage(new Image(name.getText))
    val f:File= new File("src/projeto/img/temp.png")
    if (f.delete()) Some(f) else None

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
    println("ola do add")

  }

  def onClickMirrorH():Unit={
    val tree:Tree= rightFile
    tree.treeToImage( "src/projeto/img/temp.png", "png",Effects(tree.imageToTree()).mirrorH())
    imageView.setImage(new  Image("projeto/img/temp.png"))

  }
  var count = 0

  def onClickMirrorV ():Unit={
    val tree:Tree= rightFile()
    val teste_makeTree = tree.imageToTree()
    val effects:Effects = Effects(teste_makeTree)
    tree.treeToImage( "src/projeto/count/"+ count.toString+ ".png", "png",effects.mirrorV())
    imageView.setImage(new  Image("projeto/count/"+ count.toString+ ".png"))
    count+=1
  }
  def rightFile () : Tree= {
    println("count1:" +"src/projeto/count/"+ (count-1).toString+ ".png")
    if (scala.reflect.io.File("src/projeto/count/"+ (count-1).toString+ ".png").exists) {
      println("o ficheiro já existe na pasta count")
      val t:Tree= Tree("src/projeto/count/"+ (count-1).toString+ ".png")
      t
    } else {
      val s = "src/" + name.getText
      println("name da img: " +s)
      val r: Tree=Tree(s)
      r
    }
  }
  def rightFileR () : Tree= {
    if (scala.reflect.io.File("src/projeto/img/temp.png").exists) {
      println("existe")
      val t:Tree= Tree("src/projeto/img/temp.png")
      t
    } else {
      val s = "src/" + name.getText
      val r: Tree=Tree(s)
      r
    }
  }








  /*
 def rightFile () : Tree= {
    if (FxApp1.isEdited) {
      println("ola Rita")
      Tree("src/projeto/img/~temp.png")
    } else {
      Tree("src/" + name.getText())
    }
  }*/





}
