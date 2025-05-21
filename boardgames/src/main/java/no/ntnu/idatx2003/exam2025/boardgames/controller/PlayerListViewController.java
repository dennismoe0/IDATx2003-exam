package no.ntnu.idatx2003.exam2025.boardgames.controller;

import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import no.ntnu.idatx2003.exam2025.boardgames.service.StatsManager;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;
import no.ntnu.idatx2003.exam2025.boardgames.view.PlayerListView;

/**
 * Controller class for managing the player list view.
 * Handles retrieval and deletion of players using the PlayerDaoImpl.
 */
public class PlayerListViewController {

  private final PlayerDaoImpl playerDao;
  private final Map<String, StatsManager<?>> statsManagers;
  private final GameSession gameSession;
  private HashMap<Player, String> playingPieceMap = new HashMap<>();
  private static final Logger log = Log.get(PlayerListViewController.class);

  // needs reference to the DAO for loading saved players.
  // needs methods for populating the list view I guess?
  // private final PlayerDaoImpl playerDAO;
  // private final List<Player> playerList;

  /**
   * Constructs a PlayerListViewController with the specified PlayerDaoImpl.
   *
   * @param playerDao the PlayerDaoImpl used to access player data
   */
  public PlayerListViewController(
      PlayerDaoImpl playerDao, Map<String, StatsManager<?>> statsManagers, GameSession gameSession) {
    this.playerDao = playerDao;
    this.statsManagers = statsManagers;
    this.gameSession = gameSession;
  }

  /**
   * Retrieves all players from database.
   *
   * @return a list of all players
   * @throws SQLException if a database access error occurs
   */
  public List<Player> getAllPlayers() throws SQLException {
    return playerDao.getAllPlayers();
  }

  /**
   * Deletes a player from the database by their player ID.
   *
   * @param playerId the ID of the player to delete
   * @throws SQLException if a database access error occurs
   */
  public void deletePlayer(int playerId) throws SQLException {
    playerDao.delete(playerId);
  }

  public void addPlayerToGameSession(Player player) {
    gameSession.addPlayer(player);
  }

  public void removePlayerFromGameSession(Player player) {
    gameSession.removePlayer(player);
  }

  public GameSession getGameSession() {
    return gameSession;
  }

  /**
   * Updates the playing piece asset path for the specified player.
   *
   * @param player    the player whose playing piece is to be updated
   * @param assetPath the asset path of the playing piece
   */
  public void updatePlayingPiece(Player player, String assetPath) {
    playingPieceMap.put(player, assetPath);
  }

  /**
   * Retrieves the asset path for the specified player's playing piece.
   *
   * @param player the player whose playing piece asset path is to be retrieved
   * @return the asset path of the player's playing piece, or a default path if
   *         not set
   */
  public String getPlayingPieceAsset(Player player) {
    return playingPieceMap.getOrDefault(player, "/assets/pieces/default_piece.png");
  }

  /**
   * Opens a piece picker dialog for the specified player and returns the selected
   * asset path.
   *
   * @param player the player for whom the piece picker is opened
   * @return the asset path of the selected playing piece, or the default if none
   *         selected
   */
  public String openPiecePicker(Player player) {
    // Create a stage for the piece picker
    Stage popup = new Stage();
    popup.initModality(Modality.APPLICATION_MODAL);

    HBox assetBox = new HBox(10);
    String[] pieceAssets = {
        "/assets/pieces/blue_piece.png", "/assets/pieces/orange_piece.png",
        "/assets/pieces/purple_piece.png", "/assets/pieces/green_piece.png",
        "/assets/pieces/pink_piece.png"
    };

    final String[] selectedAsset = new String[1];

    for (String assetPath : pieceAssets) {
      InputStream is = getClass().getResourceAsStream(assetPath);
      if (is == null) {
        is = getClass().getResourceAsStream("/assets/pieces/default_piece.png");
      }
      ImageView imageView = new ImageView(new Image(is));
      imageView.setFitWidth(50);
      imageView.setFitHeight(50);
      imageView.setPickOnBounds(true);
      imageView.setOnMouseClicked(ev -> {
        selectedAsset[0] = assetPath;
        popup.close();
      });
      assetBox.getChildren().add(imageView);
    }
    Scene scene = new Scene(assetBox);
    popup.setScene(scene);
    popup.showAndWait();

    return selectedAsset[0] != null ? selectedAsset[0] : "/assets/pieces/default_piece.png";
  }

  /**
   * Attempts to add a player to the current game session if the player limit has
   * not been reached.
   *
   * @param player the player to add to the game session
   * @return true if the player was added, false if the player limit is reached
   */
  public boolean selectPlayer(Player player) {
    if (gameSession.getPlayers().size() >= 5) {
      return false;
    }
    gameSession.addPlayer(player);
    return true;
  }

  /**
   * Checks if the specified asset path is already selected by another player in
   * the current game session.
   *
   * @param currentPlayer the player attempting to select the piece
   * @param assetPath     the asset path of the playing piece to check
   * @return true if another player has already selected the same piece, false
   *         otherwise
   */
  public boolean isDuplicatePieceSelection(Player currentPlayer, String assetPath) {
    for (Player p : gameSession.getPlayers()) {
      if (p.equals(currentPlayer)) {
        continue;
      }
      if (getPlayingPieceAsset(p).equals(assetPath)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Retrieves the statistics map for a given player and game.
   *
   * @param player   the player whose statistics are to be retrieved
   * @param gameName the name of the game for which statistics are needed
   * @return a LinkedHashMap containing statistic names and their values
   * @throws SQLException             if a database access error occurs
   * @throws IllegalArgumentException if no StatsManager is found for the
   *                                  specified game
   */
  public LinkedHashMap<String, Integer> getPlayerStatsMap(
      Player player, String gameName) throws SQLException {
    StatsManager<?> manager = statsManagers.get(gameName);
    if (manager == null) {
      throw new IllegalArgumentException("No statsmanager for game: " + gameName);
    }
    LinkedHashMap<String, Integer> stats = ((StatsManager<?>) manager).loadStatsAsMap(player);
    return stats;
  }

  public List<String> getSupportedGames() {
    return new ArrayList<>(statsManagers.keySet());
  }
}
