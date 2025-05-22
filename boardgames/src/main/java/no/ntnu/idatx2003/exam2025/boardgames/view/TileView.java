package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.css.StyleClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

/**
 * Base TileView class.
 */
public class TileView extends BorderPane {
  private Tile tile;
  private final Text tileNumber;
  private final int width;
  private final StackPane view;
  private Rectangle tileBackground;

  public TileView(Tile tile, int width, String styleClass) {
    this.tile = tile;
    this.width = width;
    tileBackground = new Rectangle();
    tileBackground.getStyleClass().add("tile-view");
    tileBackground.getStyleClass().add(styleClass);
    tileNumber = new Text();
    view = new StackPane(tileBackground);
    //view.getStyleClass().add("tile-view");
    //view.getStyleClass().add(styleClass);
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

  public Rectangle getTileBackground() {
    return tileBackground;
  }

  private void buildView() {
    view.setMaxSize(width, width);
    double minSize = 0.5 * width;
    view.setMinSize(minSize, minSize);
    view.setPrefSize(width, width);
    tileBackground.widthProperty().bind(view.widthProperty());
    tileBackground.heightProperty().bind(view.heightProperty());
    tileNumber.setText(String.valueOf(tile.getId()));

    StackPane.setAlignment(tileNumber, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(tileNumber, new Insets(0, +2, +1, 0));
    view.getChildren().add(tileNumber);
  }

}
