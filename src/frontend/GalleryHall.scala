package frontend
import javafx.scene.layout.{HBox, TilePane}
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.image.ImageView
import javafx.scene.Scene
import javafx.stage.Stage
import projeto.Gallery
import projeto.Gallery.Album
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent

import scala.annotation.tailrec


class GalleryHall extends Application {

  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Photo Gallery")
    val fxmlLoader = new FXMLLoader(getClass.getResource("controller.fxml")) //tecnicamente agora nem estamos a usar o controller.fxml

    val pane: TilePane = fxmlLoader.load()

    //meter as imageviews com as imagens que temos na lista dentro do pane
    FxApp.loadImages(pane, FxApp.album, fxmlLoader)

    //montar o scene e o stage
    val scene = new Scene(pane)

    primaryStage.setScene(scene)
    primaryStage.show()
  }


}


object FxApp {

  /*def containsUser(lst: List[(Int, String)], id: Int): Boolean = {
    lst match {
      case List() => false
      case x::xs => if(x._1 == id) true else containsUser(xs, id)
    }
  }*/

  var album: List[(Int, String)] = List((1, "projeto/img/169_300.png"), (2, "projeto/img/portrait.png"), (3, "projeto/img/img.png"), (4, "projeto/img/rita.jpeg"))
  var gallery: Gallery = new Gallery(album)


  var event: EventHandler[MouseEvent] = new EventHandler[MouseEvent] {
    override def handle(t: MouseEvent): Unit = {
      val secondStage: Stage = new Stage()
      val fxmlLoader = new FXMLLoader(getClass.getResource("controller2.fxml"))
      secondStage.setScene(new Scene(new HBox(100, new Label("Second window"))))
      secondStage.setHeight(200)
      secondStage.show()
    }
  }

  @tailrec
  def loadImages(pane: TilePane, album: Album, fxml: FXMLLoader): Unit = { //nao sei se temos de usar o pattern matching aqui
    album match {
      case Nil =>
      case x::xs =>
        val image: ImageView = new ImageView(x._2) //cria a imageview com o path que vai buscar à lista
        image.setPreserveRatio(true) //isto e pra quando definimos a altura em baixo, a largura se adapte tb à mudança
        image.setFitHeight(200) //nao concordo com isto pq acho que nao deviamos po las todas da mesma altura simplesmente deveriamos impedir que ficassem maiores que um determinado size limite
        //var controller: Controller = fxml.getController
        image.setOnMouseClicked(event)
        pane.getChildren.add(image)
        loadImages(pane, xs, fxml)
    }
  }



  def main(args: Array[String]): Unit = {
    Application.launch(classOf[GalleryHall], args: _*)
  }
}

