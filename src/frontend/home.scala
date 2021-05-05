package frontend
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class home extends Application {
  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("My Hello World App")
    val fxmlLoader =
      new FXMLLoader(getClass.getResource("controller.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
object FxApp1 {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[home], args: _*)
  }
}