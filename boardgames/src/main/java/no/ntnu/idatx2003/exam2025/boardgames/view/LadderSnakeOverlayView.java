package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.List;

import org.slf4j.Logger;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.LadderTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.SnakeTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.service.TileViewRegister;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;

/**
 * Class for rendering an overlay between the Board and the Game pieces.
 * Overlay renders snakes & ladders based on entry and exit tiles.
 */
public class LadderSnakeOverlayView extends Pane {

  private final Board board;
  private final TileViewRegister tileViewRegister;
  private static final Logger log = Log.get(LadderSnakeOverlayView.class);

  /**
   * Constructs a LadderSnakeOverlayView with the specified board and tile view
   * register.
   *
   * @param board            the board containing the tiles
   * @param tileViewRegister the register for tile views
   */
  public LadderSnakeOverlayView(Board board, TileViewRegister tileViewRegister) {
    this.board = board;
    this.tileViewRegister = tileViewRegister;
    Platform.runLater(this::drawConnections);
  }

  /**
   * Draws the connections (snakes and ladders) on the overlay.
   */
  private void drawConnections() {
    // Clears preexisting lines
    getChildren().clear();

    List<Tile> tiles = board.getTilesAsList();

    for (Tile tile : tiles) {
      if (tile.getTileStrategy() != null) {
        Tile exitTile = null;
        if (tile.getTileStrategy() instanceof LadderTileStrategy) {
          exitTile = ((LadderTileStrategy) tile.getTileStrategy()).getEndTile();
        } else if (tile.getTileStrategy() instanceof SnakeTileStrategy) {
          exitTile = ((SnakeTileStrategy) tile.getTileStrategy()).getEndTile();
        }
        if (exitTile != null) {
          // Get the TileViews for entry and exit
          var entryTileView = tileViewRegister.getTileView(tile.getId());
          var exitTileView = tileViewRegister.getTileView(exitTile.getId());
          if (entryTileView == null || exitTileView == null) {
            continue;
          }

          // center coord of entry tile
          double entryWidth = entryTileView.getView().getWidth();
          Point2D entrySceneCenter = entryTileView.getView().localToScene(
              entryWidth / 2, entryWidth / 2);

          // center coord of exit tile
          double exitWidth = exitTileView.getView().getWidth();
          Point2D exitSceneCenter = exitTileView.getView().localToScene(
              exitWidth / 2, exitWidth / 2);

          Point2D entryLocal = this.sceneToLocal(entrySceneCenter);
          Point2D exitLocal = this.sceneToLocal(exitSceneCenter);

          String assetPath = null;
          int variantCount = 2; // Number of asset variants (e.g., snake1, snake2, snake3)
          int variantIndex = tile.getId() % variantCount + 1; // 1-based index

          // Replace the number '3' with '%d' to have a variety
          // snake3 and ladder3 was produced by chatgpt
          if (tile.getTileStrategy() instanceof LadderTileStrategy) {
            assetPath = String.format("/assets/laddergame_assets/ladder1.png", variantIndex);
          } else if (tile.getTileStrategy() instanceof SnakeTileStrategy) {
            assetPath = String.format("/assets/laddergame_assets/snake2.png", variantIndex);
          }

          if (assetPath != null) {
            var assetStream = getClass().getResourceAsStream(assetPath);
            if (assetStream == null) {
              log.warn("Asset not found: {}", assetPath);
              continue;
            }
            Image image = new Image(assetStream);
            ImageView connectionView = new ImageView(image);
            connectionView.setPreserveRatio(true);

            // Calculate distance and angle between entry and exit.
            double deltaX = exitLocal.getX() - entryLocal.getX();
            double deltaY = exitLocal.getY() - entryLocal.getY();
            double distance = Math.hypot(deltaX, deltaY);
            double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));

            double extraMargin = 20;
            double adjustedDistance = distance + extraMargin;

            if (tile.getTileStrategy() instanceof LadderTileStrategy) {
              double ladderMultiplier = 1.2;
              adjustedDistance = distance * ladderMultiplier;
            } else {
              adjustedDistance = distance + extraMargin;
            }

            double thickness;
            if (tile.getTileStrategy() instanceof SnakeTileStrategy) {
              thickness = 80; // snake thickness
            } else {
              thickness = 80; // ladder thickness
              angle += 90;
            }

            // Stretch image
            if (tile.getTileStrategy() instanceof LadderTileStrategy) {
              // ladders
              connectionView.setPreserveRatio(false);
              connectionView.setFitHeight(adjustedDistance);
              connectionView.setFitWidth(thickness);

              connectionView.setRotate(angle);

              double midX = (entryLocal.getX() + exitLocal.getX()) / 2;
              double midY = (entryLocal.getY() + exitLocal.getY()) / 2;

              connectionView.setLayoutX(midX - (thickness / 2));
              connectionView.setLayoutY(midY - (adjustedDistance / 2));
            } else {
              // snakes
              connectionView.setPreserveRatio(false);
              connectionView.setFitWidth(adjustedDistance);
              connectionView.setFitHeight(thickness);
              connectionView.setRotate(angle);

              double midX = (entryLocal.getX() + exitLocal.getX()) / 2;
              double midY = (entryLocal.getY() + exitLocal.getY()) / 2;
              connectionView.setLayoutX(midX - (adjustedDistance / 2));
              connectionView.setLayoutY(midY - (thickness / 2));
            }

            getChildren().add(connectionView);
          }
        }
      }
    }
  }
}
