package no.ntnu.idatx2003.exam2025.boardgames.model;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Game session class handles players, choosing board game, etc.
 */
public class GameSession {
  private static Logger logger = Log.get(GameSession.class);
  private final List<Player> players = new ArrayList<Player>();
  private BoardGame boardGame;

  /**
   * Adds a player to the player list.
   *
   * @param player a finished player game object.
   */
  public void addPlayer(Player player) {
    logger.info("adding player " + player.getPlayerName());
    players.add(player);
  }

  /**
   * Removes a player from the player list.
   *
   * @param player the player object to be removed.
   */
  public void removePlayer(Player player) {
    logger.info("removing player " + player.getPlayerName());
    players.remove(player);
  }

  public BoardGame getBoardGame() {
    return boardGame;
  }

  public void setBoardGame(BoardGame boardGame) {
    logger.info("setting board game " + boardGame.getName());
    this.boardGame = boardGame;
  }

  public List<Player> getPlayers() {
    return players;
  }

}
