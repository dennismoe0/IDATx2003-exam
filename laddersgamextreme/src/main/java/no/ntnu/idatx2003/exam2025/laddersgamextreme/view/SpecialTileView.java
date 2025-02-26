package no.ntnu.idatx2003.exam2025.laddersgamextreme.view;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.BoardModel;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.SpecialTile;

public class SpecialTileView extends Group {

  private final SpecialTile specialTile;
  private final BoardModel boardModel;
  private final int tileSize;

  // getClass = classpath = main folder
  // Might want to add multiple images to introduce "variety", fix in method
  // buildView
  private final Image ladderImage = new Image(getClass().getResourceAsStream(
      "/assets/laddergame_assets/ladder1.png"));
  private final Image snakeImage = new Image(getClass().getResourceAsStream(
      "/assets/laddergame_assets/snake1.png"));

  /**
   * Constructs a SpecialTileView for a specific special tile (entry and exit).
   *
   * @param specialTile the special tile to be displayed
   * @param boardModel  the board model
   * @param tileSize    the size of the tile
   */
  public SpecialTileView(SpecialTile specialTile, BoardModel boardModel, int tileSize) {
    this.specialTile = specialTile;
    this.boardModel = boardModel;
    this.tileSize = tileSize;
    buildView();
  }

  private void buildView() {

    int totalRows = boardModel.getRows();
    int totalCols = boardModel.getCols();

    int[] entryModelCoords = boardModel.convertToRowColZigzag(specialTile.getEntryTile(), totalCols);
    System.out.println("Ladder image loaded: " + (ladderImage.getWidth() > 0));
    int[] exitModelCoords = boardModel.convertToRowColZigzag(specialTile.getExitTile(), totalCols);
    System.out.println("Snake image loaded: " + (snakeImage.getWidth() > 0));

    int entryUiRow = totalRows - 1 - entryModelCoords[0];
    int exitUiRow = totalRows - 1 - exitModelCoords[0];

    // Find center + find place to render image based on entry and exit
    double entryX = entryModelCoords[1] * tileSize;
    double entryY = entryUiRow * tileSize;
    double exitX = exitModelCoords[1] * tileSize;
    double exitY = exitUiRow * tileSize;

    double topY = Math.min(entryY, exitY);
    double bottomY = Math.max(entryY, exitY);
    double height = bottomY - topY; // Height of image

    double centerX = (entryX + exitX) / 2.0;

    ImageView imageView = new ImageView();
    // Ladders and snakes after
    if (specialTile.getTileType() == SpecialTile.TileType.LADDER) {
      imageView.setImage(ladderImage);
    } else if (specialTile.getTileType() == SpecialTile.TileType.SNAKE) {
      imageView.setImage(snakeImage);
    }

    // Width of image
    double width = tileSize * 0.6;
    imageView.setFitWidth(width);
    imageView.setFitHeight(height);

    // Position image correctly
    imageView.setLayoutX(centerX - width / 2.0);
    imageView.setLayoutY(topY);

    this.getChildren().add(imageView);
  }
}
