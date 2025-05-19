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
      circle.setVisible(false); // Hide initially
      pieceNodes.put(piece, circle);
      getChildren().add(circle);

      // Listen for tile changes
      piece.currentTileProperty().addListener((obs, oldTile, newTile) -> {
        updateAllPiecesOnTile(newTile, tileViewRegister);
        if (oldTile != null) {
          updateAllPiecesOnTile(oldTile, tileViewRegister);
        }
      });
    }

    sceneProperty().addListener((obs, oldScene, newScene) -> {
      if (newScene != null) {
        pieces.forEach(piece -> updateAllPiecesOnTile(piece.getCurrentTile(), tileViewRegister));
      }
    });
  }

  private void updateAllPiecesOnTile(Tile tile, TileViewRegister tileViewRegister) {
    if (tile == null) {
      return;
    }
    TileView tileView = tileViewRegister.getTileView(tile.getId());
    if (tileView == null) {
      return;
    }
    List<GamePiece> piecesOnTile = pieceNodes.keySet().stream()
        .filter(p -> p.getCurrentTile() != null && p.getCurrentTile().getId() == tile.getId())
        .toList();
    for (GamePiece piece : piecesOnTile) {
      Circle circle = pieceNodes.get(piece);
      updatePiecePosition(circle, tile, tileViewRegister, piecesOnTile);
    }
  }

  private void updatePiecePosition(Circle node, Tile tile, TileViewRegister tileViewRegister,
      List<GamePiece> piecesOnTile) {
    if (tile == null) {
      node.setVisible(false);
      return;
    } else {
      node.setVisible(true);
    }

    int count = piecesOnTile.size();
    int index = piecesOnTile.indexOf(
        pieceNodes.entrySet().stream().filter(e -> e.getValue() == node)
            .map(Map.Entry::getKey).findFirst().orElse(null));

    TileView tileView = tileViewRegister.getTileView(tile.getId());
    if (tileView != null) {
      double tileSize = tileView.getView().getWidth();

      // Use larger offsets for more separation
      // it'x x and y position within the tile
      // each bracket represents 1 piece
      double[][] positions;
      switch (count) {
        case 1 -> positions = new double[][] {
            { 0.5, 0.5 } };
        case 2 -> positions = new double[][] {
            { 0.3, 0.5 }, { 0.7, 0.5 }
        };
        case 3 -> positions = new double[][] {
            { 0.3, 0.3 }, { 0.7, 0.3 }, { 0.5, 0.65 }
        };
        case 4 -> positions = new double[][] {
            { 0.3, 0.3 }, { 0.7, 0.3 }, { 0.3, 0.65 }, { 0.7, 0.65 }
        };
        case 5 -> positions = new double[][] {
            { 0.25, 0.25 },
            { 0.75, 0.25 },
            { 0.25, 0.65 },
            { 0.75, 0.65 },
            { 0.5, 0.45 }
        };
        case 6 -> positions = new double[][] {
            { 0.25, 0.25 },
            { 0.5, 0.25 },
            { 0.75, 0.25 },
            { 0.25, 0.65 },
            { 0.5, 0.65 },
            { 0.75, 0.65 }
        };
        default -> {
          int cols = (int) Math.ceil(Math.sqrt(count));
          int rows = (int) Math.ceil((double) count / cols);
          positions = new double[count][2];

          double usableSpace = 0.6;
          double xMargin = (1.0 - usableSpace) / 2;
          double yMargin = (1.0 - usableSpace) / 2;

          double topMargin = yMargin;
          double bottomMargin = yMargin + 0.1;

          // Calculate spacing between pieces
          double xSpacing = usableSpace / (cols >= 2 ? cols - 1 : 1);
          double ySpacing = (1.0 - (topMargin + bottomMargin)) / (rows >= 2 ? rows - 1 : 1);
          double xStart = xMargin;
          double yStart = topMargin;

          for (int i = 0; i < count; i++) {
            int col = i % cols;
            int row = i / cols;
            positions[i][0] = xStart + (xSpacing * col);
            positions[i][1] = yStart + (ySpacing * row);
          }
        }
      }

      double minRadius = Math.max(8, tileSize * 0.13);
      double maxRadius = tileSize * 0.22;
      double tokenRadius = Math.max(maxRadius - (count - 1) * 4, minRadius);
      node.setRadius(tokenRadius);

      double centerX = tileView.getView().localToScene(tileSize / 2, tileSize / 2).getX();
      double centerY = tileView.getView().localToScene(tileSize / 2, tileSize / 2).getY();
      double localX = this.sceneToLocal(centerX, centerY).getX();
      double localY = this.sceneToLocal(centerX, centerY).getY();

      double offsetX = (positions[index][0] - 0.5) * tileSize;
      double offsetY = (positions[index][1] - 0.5) * tileSize;

      node.setLayoutX(localX + offsetX);
      node.setLayoutY(localY + offsetY);
    }
  }

}
