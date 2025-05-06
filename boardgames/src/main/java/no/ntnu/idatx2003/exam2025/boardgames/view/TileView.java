package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.css.StyleClass;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

/**
 * Base TileView class.
 */
public class TileView {
  private Tile tile;
  private Text tileNumber;
  private int width;
  private StackPane view;

  public TileView(Tile tile, int width, String styleClass) {
    this.tile = tile;
    this.width = width;
    tileNumber = new Text();
    view = new StackPane();
    view.getStyleClass().add(styleClass);
    buildView();
  }
  public Tile getTile() {
    return tile;
  }
  public void setTile(Tile tile) {
    this.tile = tile;
  }

  private void buildView() {
    view.setMaxSize(width, width);
    double minSize = 0.5 * width;
    view.setMinSize(minSize, minSize);
    view.setPrefSize(width, width);
    tileNumber.setText(String.valueOf(tile.getId()));
    view.getChildren().add(tileNumber);
  }

}
