package frontend

import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.{Scene, image}
import javafx.scene.canvas.Canvas
import javafx.scene.control.{Button, ButtonBar, Label, TextField, ToggleButton}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.{HBox, TilePane}
import javafx.scene.media.MediaView
import javafx.stage.Stage

class Controller {

  @FXML
  private var pane: TilePane = _



  def onButtonClicked(): Unit = {
    val secondStage: Stage = new Stage()
    secondStage.setScene(new Scene(new HBox(9, new Label("Second window"))))
    secondStage.show()
  }


  /*def onButtonClicked2(): Unit = {
    //println()
    val n = textUsername.getText.trim.toInt
    if(FxApp.containsUser(FxApp.list, n))
      textYes.setVisible(true)
    else textNo.setVisible(true)
  }*/

}

