package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.service.TileViewRegister;

public class GamePieceView extends Pane {
  // After BoardView creation -> Get a list from boardgame class (i.e.
  // ladderboardgame)
  // get list a gamepieces from the boardgame (ladderboardgame, input based)
  // BoardView shows the board, so it needs to be on top of that.
  // -> Update gamepiece with observable ID for it's current tile position that is
  // used for movement.
  // GamePieces should be able to be on the same tile (rezising), and should be
  // connected visually to the tile their on
  // I.e. if a piece from player 1 is on tile 4, it should be shown there on top
  // of BoardView's view of the board.
  // Since BoardView is a part of BoardGameView this should be a part of that.
  // Summary: Visual pieces based on backend location.

  private final Map<GamePiece, Circle> pieceNodes = new HashMap<>();

  public GamePieceView(List<GamePiece> pieces, TileViewRegister tileViewRegister) {
    for (GamePiece piece : pieces) {
      Circle circle = new Circle(15, piece.getPlayer().getPlayerColor());
      pieceNodes.put(piece, circle);
      getChildren().add(circle);

      // Listen for tile changes
      piece.currentTileProperty().addListener((obs, oldTile, newTile) -> {
        updatePiecePosition(circle, newTile, tileViewRegister);
      });

      // Initial placement
      updatePiecePosition(circle, piece.getCurrentTile(), tileViewRegister);
    }
  }

  private void updatePiecePosition(Circle node, Tile tile, TileViewRegister tileViewRegister) {

    if (tile == null) {
      node.setVisible(false);
    } else {
      node.setVisible(true);
    }
    TileView tileView = null;
    if (tile != null) {
      tileView = tileViewRegister.getTileView(tile.getId());
    }
    if (tileView != null) {
      double centerX = tileView.getView()
          .localToScene(
              tileView.getView().getWidth() / 2, tileView.getView().getHeight() / 2)
          .getX();
      double centerY = tileView.getView()
          .localToScene(
              tileView.getView().getWidth() / 2, tileView.getView().getHeight() / 2)
          .getY();

      double localX = this.sceneToLocal(centerX, centerY).getX();
      double localY = this.sceneToLocal(centerX, centerY).getY();

      node.setLayoutX(localX);
      node.setLayoutY(localY);
    }

  }
}
