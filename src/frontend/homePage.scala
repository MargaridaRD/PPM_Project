package frontend

import javafx.application.Application
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage


import javafx.scene.control.{Button}

class homePage{

  @FXML
  private var button1: Button = _

  def seePhotos(): Unit = {
   /* val fxmlLoader = new FXMLLoader(getClass.getResource("Controller.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)

    val secondStage: Stage = new Stage()
    secondStage.setScene(scene)
    secondStage.show()

    */
    println("Hello World")
  }

}
