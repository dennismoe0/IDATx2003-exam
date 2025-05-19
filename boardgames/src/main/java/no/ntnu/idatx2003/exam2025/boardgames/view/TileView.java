package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.css.StyleClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

/**
 * Base TileView class.
 */
public class TileView extends BorderPane {
  private Tile tile;
  private Text tileNumber;
  private int width;
  private StackPane view;

  public TileView(Tile tile, int width, String styleClass) {
    this.tile = tile;
    this.width = width;
    tileNumber = new Text();
    view = new StackPane();
    view.getStyleClass().add("tile-view");
    view.getStyleClass().add(styleClass);
    tileNumber.getStyleClass().add("tile-number");
    buildView();
    super.setCenter(view);
  }

  public Tile getTile() {
    return tile;
  }

  public void setTile(Tile tile) {
    this.tile = tile;
  }

  public StackPane getView() {
    return view;
  }

  private void buildView() {
    view.setMaxSize(width, width);
    double minSize = 0.5 * width;
    view.setMinSize(minSize, minSize);
    view.setPrefSize(width, width);
    tileNumber.setText(String.valueOf(tile.getId()));

    StackPane.setAlignment(tileNumber, Pos.BOTTOM_LEFT);
    StackPane.setMargin(tileNumber, new Insets(0, 0, +1, +2));
    view.getChildren().add(tileNumber);
  }

}
