package no.ntnu.idatx2003.exam2025.boardgames.model.tile;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;

public interface TileStrategy {
  void applyEffect(GamePiece gamePiece);
}
