package no.ntnu.idatx2003.exam2025.boardgames;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.LudoStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.SnakesAndLaddersStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.LudoStats;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;
import no.ntnu.idatx2003.exam2025.boardgames.service.DatabaseManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.GameEventHandlerImpl;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;
import no.ntnu.idatx2003.exam2025.boardgames.service.StatsManager;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import no.ntnu.idatx2003.exam2025.boardgames.util.ViewFactory;
import org.slf4j.Logger;

/**
 * The main Application File for starting and stopping the program.
 */
public class BoardGameApplication extends Application {
  private static final Logger log = Log.get(BoardGameApplication.class);
  private Connection connection;

  // Dennis note: Need to add connection to creation of Board Game to instantiate
  // the database connection for stats
  @Override
  public void start(Stage primaryStage) throws Exception {

    try {
      connection = DatabaseManager.connect();
      DatabaseManager.initializeDatabase(connection);
    } catch (SQLException e) {
      log.error(e.getMessage());
      return;
    }

    log.info("Program Launched");

    PlayerDaoImpl playerDao = new PlayerDaoImpl(connection);

    // Temporary for testing persistence
    if (playerDao.getAllPlayers().isEmpty()) {
      Player player1 = new Player(0, "Dennis", 24);
      Player player2 = new Player(0, "Sasha", 27);
      int id1 = playerDao.create(player1);
      int id2 = playerDao.create(player2);
      player1.setPlayerId(id1);
      player2.setPlayerId(id2);
      player1.setPlayerColor(javafx.scene.paint.Color.BLUE);
      player2.setPlayerColor(javafx.scene.paint.Color.GREEN);

      // Add 10 more players with Latin names (Copilot generated test people)
      String[] latinNames = {
          "Marcus", "Lucius", "Gaius", "Tiberius", "Aurelia",
          "Claudia", "Octavia", "Cornelia", "Flavia", "Julia"
      };
      int[] ages = { 30, 28, 35, 22, 26, 31, 29, 33, 27, 25 };
      for (int i = 0; i < latinNames.length; i++) {
        Player latinPlayer = new Player(0, latinNames[i], ages[i]);
        int latinId = playerDao.create(latinPlayer);
        latinPlayer.setPlayerId(latinId);
      }
    }
    SceneManager sceneManager = new SceneManager(primaryStage);
    GameSession gameSession = new GameSession();
    SceneRegister sceneRegister = new SceneRegister();
    ViewFactory viewFactory = new ViewFactory();
    GameEventHandlerImpl eventHandler = new GameEventHandlerImpl();
    gameSession.setGameEventHandler(eventHandler);

    // stats
    StatsManager<SnakesAndLaddersStats> snakesStatsManager = new StatsManager<>(
        playerDao,
        new SnakesAndLaddersStatsDaoImpl(connection));
    StatsManager<LudoStats> ludoStatsManager = new StatsManager<>(
        playerDao, new LudoStatsDaoImpl(connection));

    // Create the map
    Map<String, StatsManager<?>> statsManagers = new LinkedHashMap<>();
    statsManagers.put("Snakes and Ladders", snakesStatsManager);
    statsManagers.put("Ludo", ludoStatsManager);

    log.info("Registering Scenes");
    sceneRegister.register("main-menu", () -> viewFactory.buildMainMenuView(sceneRegister, sceneManager));
    sceneRegister.register("ladder-game", () -> viewFactory.buildLadderBoardGameView(gameSession.getBoardGame(),
        sceneManager, sceneRegister, gameSession));
    sceneRegister.register("add-player", () -> viewFactory.buildAddPlayerView(gameSession, sceneManager, playerDao));
    sceneRegister.register("build-game",
        () -> viewFactory.buildGameBuilderView(gameSession, sceneManager, sceneRegister, playerDao, statsManagers));
    sceneRegister.register("player-statistics",
        () -> viewFactory.buildPlayerStatsListView(playerDao, statsManagers, gameSession, sceneRegister, sceneManager));

    sceneRegister.register("player-list", () -> viewFactory.buildPlayerListView(playerDao, statsManagers, gameSession));

    log.info("Building Default Window");

    log.info("Setting up GUI");
    Parent initial = sceneRegister.get("main-menu");

    log.info("Initial scene: " + initial);
    sceneManager.initialize(initial);
    log.info("Launching GUI");
    primaryStage.show();
  }

  /**
   * The main method for running the program.
   *
   * @param args default args.
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void stop() {
    log.info("Initiating shutdown.");
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
        log.info("Connection closed");
      }
    } catch (SQLException e) {
      log.error("Shutdown encountered an error: {}", e.getMessage());
    }
  }
}
