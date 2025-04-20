package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

import java.util.ArrayList;
import java.util.List;

public class LadderBoardGame extends BoardGame {
  private int NUMBER_OF_PIECES = 1;
  private int NUMBER_OF_DICE = 1;
  private Dice dice;
  private GamePiece currentGamePiece;


  @Override
  public void setUp(List<Player> players){
    List<GamePiece> pieces = new ArrayList<GamePiece>();
    for (Player player : players) {
      pieces.clear();
      pieces.add(new GamePiece());
      super.addPlayerPieces(player, pieces);
    }
  }

  @Override
  public void takeTurn(Player player) {

  }


}
