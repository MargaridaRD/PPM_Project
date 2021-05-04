package frontend

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage
import projeto.Gallery

class Album extends Application {
  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Album")
    val fxmlLoader =
      new FXMLLoader(getClass.getResource("Controller.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)
    primaryStage.setScene(scene)
    primaryStage.show()
  }


}
object FxApp {

  val gallery1 = Gallery (List((0,"src/projeto/img/rita.jpeg"),(1,"src/projeto/img/objc2_2.png"),(2,"src/projeto/img/objc2_3.png")))

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Album], args: _*)
  }

}
