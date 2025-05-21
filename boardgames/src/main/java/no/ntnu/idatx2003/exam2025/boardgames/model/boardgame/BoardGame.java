package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;

/**
 * An abstract class to be used when defining custom board games.
 */
public abstract class BoardGame {
  private Board board;
  private String name;
  private final HashMap<Player, List<GamePiece>> playerPieces = new HashMap<>();
  private final BooleanProperty gameIsOver = new SimpleBooleanProperty(false);
  private Player winner;

  /**
   * A method where each board game should initialize itself.
   *
   * @param players the players that will be participating in the game.
   */
  public void setUp(List<Player> players) {

  }

  /**
   * A method for retrieving the board from the game.
   *
   * @return returns a board object.
   */
  public Board getBoard() {
    return board;
  }

  /**
   * a method for setting the board of the game.
   *
   * @param board takes a board object.
   */
  public void setBoard(Board board) {
    this.board = board;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Adds a player and list of associated pieces to a map for tracking during
   * gameplay.
   *
   * @param player the player object to which the pieces are associated.
   * @param pieces the pieces associated with the player object.
   */
  public void addPlayerPieces(Player player, List<GamePiece> pieces) {
    playerPieces.put(player, pieces);
  }

  /**
   * A method for getting only the first piece associated with a player.
   *
   * @param player the player object whose piece you want to get.
   * @return returns the first GamePiece object associated with a player.
   */
  public GamePiece getFirstPlayerPiece(Player player) {
    return playerPieces.get(player).getFirst();
  }

  /**
   * A method for getting a list of all pieces associated with a player,
   * for multi-piece board games.
   *
   * @param player the player object the pieces are associated with
   * @return returns a list of all objects associated with that player.
   */
  public List<GamePiece> getPlayerPieces(Player player) {
    return playerPieces.get(player);
  }

  /**
   * Retrieves a list of all game pieces associated with all players.
   *
   * @return a list containing all GamePiece objects in the game.
   */
  public List<GamePiece> getAllGamePieces() {
    List<GamePiece> allPieces = new ArrayList<>();
    for (List<GamePiece> pieces : playerPieces.values()) {
      allPieces.addAll(pieces);
    }
    return allPieces;
  }

  /**
   * An empty/default method intended to be overridden. Used for initiating a turn
   * phase.
   */
  public void takeTurn() {

  }

  public BooleanProperty getGameOverProperty() {
    return gameIsOver;
  }

  /**
   * A method for changing the observable boolean gameIsOver.
   */
  protected void gameIsOver() {
    gameIsOver.set(true);
  }

  public void setWinner(Player player) {
    winner = player;
  }

  public Player getWinner() {
    return winner;
  }

}
