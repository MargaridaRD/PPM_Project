package frontend

import javafx.fxml.FXML
import javafx.scene.control.ScrollPane
import javafx.scene.layout.TilePane

class Controller {

  @FXML
  private var scroll: ScrollPane = _
  @FXML
  private var tile: TilePane = _

  def getTile(): TilePane = {
    tile
  }

  def getScroll(): ScrollPane = {
    scroll
  }

}
