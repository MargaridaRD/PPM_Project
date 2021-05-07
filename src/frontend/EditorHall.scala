package frontend
import javafx.application.Application
import javafx.fxml.FXMLLoader

import javafx.scene.{Parent, Scene}
import javafx.stage.Stage
import projeto.Gallery
import projeto.Gallery.Album


import java.io.{File, FileNotFoundException}
import java.util.{ArrayList, Scanner}


class EditorHall extends Application {
  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Photo Gallery")
    val fxmlLoader =
      new FXMLLoader(getClass.getResource("controller.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()

    val controller: Controller = fxmlLoader.getController
    if(!FxApp1.album.isEmpty) controller.setMainImage(FxApp1.album.head._2,FxApp1.album.head._1)
    println(controller.rightFile2())

    val scene = new Scene(mainViewRoot)
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
object FxApp1 {

  var album = create_album()
  var isEdited = false

  def readFile(s: String): List[String] = {
    var album = List[String]()
    val file = new File(s)
    try {
      val scanner = new Scanner(file)
      try {
        while (scanner.hasNextLine) {
          val line = scanner.nextLine
          album = album:+line
        }
        scanner.close()
      } catch {
        case e: FileNotFoundException =>
          System.err.println("Não encontrou o ficheiro!!")
          System.exit(1)
      } finally if (scanner != null) scanner.close()
    }
    album
  }

  def create_album(): Album = {
    val gallery = new Gallery(List())
    val list = readFile("src/frontend/album.txt")
    def aux(l: List[String], galleryAux: Gallery): Album = {
      l match {
        case Nil => galleryAux.album
        case x::xs => {
          val g: Gallery = Gallery(galleryAux.insert(x))
          aux(xs, g)
        }
      }
    }
    aux(list, gallery)
  }


  def main(args: Array[String]): Unit = {
    Application.launch(classOf[EditorHall], args: _*)

  }
}