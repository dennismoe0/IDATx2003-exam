package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

public class LadderBoardGame extends BoardGame {

  private Dice dice;
  public LadderBoardGame(Dice dice) {
    this.dice = dice;
  }

  @Override
  public void takeTurn(GamePiece gamePiece) {
    gamePiece.move(dice.rollAllDiceSum());
  }


}
