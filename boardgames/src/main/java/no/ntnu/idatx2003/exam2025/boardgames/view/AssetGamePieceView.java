package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import no.ntnu.idatx2003.exam2025.boardgames.controller.PlayerListViewController;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.service.TileViewRegister;

public class AssetGamePieceView extends Pane {
  private final Map<GamePiece, ImageView> pieceNodes = new HashMap<>();

  /**
   * Constructs an AssetGamePieceView for the given game pieces and tile view
   * register.
   *
   * @param pieces           the list of game pieces to display
   * @param tileViewRegister the register to retrieve tile views
   */
  public AssetGamePieceView(List<GamePiece> pieces, TileViewRegister tileViewRegister) {

    for (GamePiece piece : pieces) {
      String assetPath = piece.getPlayer().getPlayerPieceAssetPath();
      // Load asset, user selection
      if (assetPath == null || assetPath.isEmpty()) {
        assetPath = "/assets/playingPieces/default_piece.png";
      }
      Image assetImage = new Image(getClass().getResourceAsStream(assetPath));
      ImageView imageView = new ImageView(assetImage);

      // Adjust the image view, similar to GamePieceView
      imageView.setFitWidth(30);
      imageView.setFitHeight(30);
      imageView.setVisible(false);
      imageView.getStyleClass().add("piece-view");

      pieceNodes.put(piece, imageView);
      getChildren().add(imageView);

      // tile changes
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
      ImageView iv = pieceNodes.get(piece);
      updatePiecePosition(iv, tile, tileViewRegister, piecesOnTile);
    }
  }

  private void updatePiecePosition(ImageView iv, Tile tile, TileViewRegister tileViewRegister,
      List<GamePiece> piecesOnTile) {
    if (tile == null) {
      iv.setVisible(false);
      return;
    } else {
      iv.setVisible(true);
    }

    int count = piecesOnTile.size();
    int index = piecesOnTile.indexOf(
        pieceNodes.entrySet().stream().filter(e -> e.getValue() == iv)
            .map(Map.Entry::getKey).findFirst().orElse(null));

    TileView tileView = tileViewRegister.getTileView(tile.getId());
    if (count <= 3) {
      iv.setScaleX(1.5);
      iv.setScaleY(1.5);
    } else {
      iv.setScaleX(1.0);
      iv.setScaleY(1.0);
    }
    if (tileView != null) {
      double tileSize = tileView.getView().getWidth();
      double[][] positions;
      switch (count) {
        case 1 -> positions = new double[][] {
            { 0.5, 0.5 }
        };
        case 2 -> positions = new double[][] {
            { 0.3, 0.5 }, { 0.7, 0.5 }
        };
        case 3 -> positions = new double[][] {
            { 0.3, 0.3 }, { 0.7, 0.3 }, { 0.5, 0.65 }
        };
        case 4 -> positions = new double[][] {
            { 0.3, 0.3 }, { 0.7, 0.3 }, { 0.3, 0.65 }, { 0.7, 0.65 }
        };
        default -> {
          int cols = (int) Math.ceil(Math.sqrt(count));
          int rows = (int) Math.ceil((double) count / cols);
          positions = new double[count][2];
          double usableSpace = 0.6;
          double xmargin = (1.0 - usableSpace) / 2;
          double ymargin = (1.0 - usableSpace) / 2;
          double topMargin = ymargin;
          double xspacing = usableSpace / (cols >= 2 ? cols - 1 : 1);
          double yspacing = (1.0 - (topMargin * 2)) / (rows >= 2 ? rows - 1 : 1);
          for (int i = 0; i < count; i++) {
            int col = i % cols;
            int row = i / cols;
            positions[i][0] = xmargin + (xspacing * col);
            positions[i][1] = topMargin + (yspacing * row);
          }
        }
      }

      double centerX = tileView.getView().localToScene(tileSize / 2, tileSize / 2).getX();
      double centerY = tileView.getView().localToScene(tileSize / 2, tileSize / 2).getY();
      double localX = this.sceneToLocal(centerX, centerY).getX();
      double localY = this.sceneToLocal(centerX, centerY).getY();
      double offsetX = (positions[index][0] - 0.5) * tileSize;
      double offsetY = (positions[index][1] - 0.5) * tileSize;

      double shiftX = 15;
      double shiftY = 15;

      iv.setLayoutX(localX + offsetX - shiftY);
      iv.setLayoutY(localY + offsetY - shiftX);
    }
  }
}