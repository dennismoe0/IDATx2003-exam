package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;

import java.util.HashMap;
import java.util.List;

public abstract class BoardGame {
  private Board board;
  private String name;
  private HashMap<Player, List<GamePiece>> playerPieces = new HashMap<>();

  public void setUp(List<Player> players) {

  }

  public Board getBoard() {
    return board;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addPlayerPieces(Player player, List<GamePiece> pieces) {
    playerPieces.put(player, pieces);
  }

  public GamePiece getFirstPlayerPiece(Player player) {
    return playerPieces.get(player).getFirst();
  }

  public List<GamePiece> getPlayerPieces(Player player) {
    return playerPieces.get(player);
  }

  public void takeTurn(Player player) {

  }

}
