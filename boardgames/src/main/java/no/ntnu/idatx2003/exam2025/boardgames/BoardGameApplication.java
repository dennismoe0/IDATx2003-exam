package no.ntnu.idatx2003.exam2025.boardgames;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.GameStatsDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.SnakesAndLaddersStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardFactory;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;
import no.ntnu.idatx2003.exam2025.boardgames.service.DatabaseManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;
import no.ntnu.idatx2003.exam2025.boardgames.service.StatsManager;
import no.ntnu.idatx2003.exam2025.boardgames.util.GsonFileReader;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import no.ntnu.idatx2003.exam2025.boardgames.util.ViewFactory;
import no.ntnu.idatx2003.exam2025.boardgames.view.BoardGameView;
import org.slf4j.Logger;

/**
 * The main Application File for starting and stopping the program.
 */
public class BoardGameApplication extends Application {
  private static final Logger log = Log.get(BoardGameApplication.class);

  // Dennis note: Need to add connection to creation of Board Game to instantiate
  // the database connection for stats
  @Override
  public void start(Stage primaryStage) throws Exception {

    Connection connection;
    try {
      connection = DatabaseManager.connect();
      DatabaseManager.initializeDatabase(connection);
    } catch (SQLException e) {
      log.error(e.getMessage());
    }

    log.info("Program Launched");

    SceneManager sceneManager = new SceneManager(primaryStage);
    GameSession gameSession = new GameSession();
    SceneRegister sceneRegister = new SceneRegister();
    ViewFactory viewFactory = new ViewFactory();


    log.info("Registering Scenes");
    sceneRegister.register("main-menu", () ->
        viewFactory.buildMainMenuView(sceneRegister, sceneManager));
    sceneRegister.register("ladder-game", () ->
        viewFactory.buildLadderBoardGameView(gameSession.getBoardGame()));
    sceneRegister.register("add-player", () -> viewFactory.buildAddPlayerView(gameSession, sceneManager));
    sceneRegister.register("build-game", () -> viewFactory.buildGameBuilderView(gameSession, sceneManager, sceneRegister));

    log.info("Building Default Window");

    initializeGameSession(gameSession);

    log.info("Setting up GUI");
    Parent initial = sceneRegister.get("ladder-game");
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

  private void initializeGameSession(GameSession gameSession) {
    log.info("Initializing GameSession");
    Player player1 = new Player(1, "Dennis", 24);
    Player player2 = new Player(2, "Sasha", 27);
    gameSession.addPlayer(player1);
    gameSession.addPlayer(player2);
    try {
      gameSession.setBoardGame(buildBoardGame(gameSession.getPlayers()));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  private BoardGame buildBoardGame(List<Player> players) throws Exception {
    log.info("Building BoardGame");
    LadderBoardGame ladderBoardGame = new LadderBoardGame(getBoard(), players);
    return ladderBoardGame;
  }

  private Board getBoard() throws Exception {
    log.info("Building Board");
    BoardFactory factory = new BoardFactory();
    GsonFileReader reader = new GsonFileReader();
    Board board = factory.buildBoardFromJson(reader.readJson(
        "src/main/resources/assets/boards/laddergameboards/laddergame_special30.json"));
    return board;
  }

  private BoardGameView createBoardGameView() throws Exception {
    BoardFactory factory = new BoardFactory();
    GsonFileReader reader = new GsonFileReader();
    Board board = factory.buildBoardFromJson(reader.readJson(
        "src/main/resources/assets/boards/laddergameboards/laddergame_classic.json"));

    List<Player> players = new ArrayList<Player>();
    Player player1 = new Player(1, "Dennis", 24);
    players.add(player1);
    Player player2 = new Player(2, "Sasha", 27);
    players.add(player2);

    LadderBoardGame ladderBoardGame = new LadderBoardGame(board, players);
    return new BoardGameView("Snake's n Ladders", ladderBoardGame);
  }

//  private Scene databaseStuff(Log log, BoardView boardview) {
//    Scene scene = new Scene(boardview.asParent(),0,0);
//    // Connect to database
//    try (Connection connection = DatabaseManager.connect()) {
//
//      MenuView view = new MenuView("Main Menu", buildTestMenu());
//
//      BoardFactory factory = new BoardFactory();
//      GsonFileReader reader = new GsonFileReader();
//      Board board = factory.buildBoardFromJson(reader.readJson(
//          "src/main/resources/assets/boards/laddergameboards/laddergame_classic.json"));
//
//      // Validate board
//      if (board == null || board.getTile(1) == null) {
//        log.error("Board is not properly initialized. Please check the JSON file.");
//        return;
//      }
//
//      // Create test players
//      List<Player> players = new ArrayList<>();
//      players.add(new Player(0, "Player 1", 25));
//      players.add(new Player(0, "Player 2", 27));
//
//      // Create PlayerDAO
//      PlayerDao playerDao = new PlayerDaoImpl(connection);
//
//      // Assign stats
//      for (Player player : players) {
//        int playerId = playerDao.create(player);
//        player.setPlayerId(playerId);
//        //player.setPlayerStats(new SnakesAndLaddersStats());
//        log.info("Created player with ID {}: {}", playerId, player.getPlayerName());
//      }
//
//      // Create game
//      LadderBoardGame game = new LadderBoardGame(board, players);
//
//      // Test the game logic
//      log.info("Starting basic test of LadderBoardGame...");
//      log.info("Initial player: {}", players.get(0).getPlayerName());
//
//      // Simulate a few turns
//      for (int i = 0; i < 5; i++) {
//        log.info("Turn {}: Current player is {}", i + 1, game.getCurrentPlayer().getPlayerName());
//        try {
//          game.takeTurn();
//          GamePiece piece = game.getFirstPlayerPiece(game.getCurrentPlayer());
//          if (piece == null || piece.getCurrentTile() == null) {
//            log.error(
//                "Player {} has no valid game piece or current tile.",
//                game.getCurrentPlayer().getPlayerName());
//          } else {
//            log.info(
//                "Player {} moved. Current tile: {}",
//                game.getCurrentPlayer().getPlayerName(),
//                (piece.getCurrentTile() != null ? piece.getCurrentTile() : "Unknown"));
//          }
//        } catch (IllegalArgumentException e) {
//          log.error("Invalid move: {}", e.getMessage(), e);
//        } catch (Exception e) {
//          log.error("An unexpected error occurred: {}", e.getMessage(), e);
//        }
//      }
//
//      // Check if the game is over
//      if (game.isGameOver()) {
//        log.info("Game over! Winner: {}", game.getCurrentPlayer().getPlayerName());
//      } else {
//        log.info("Game is still ongoing.");
//      }
//
//      BoardView boardView = new BoardView(board);
//      // Scene scene = new Scene(view.asParent(), 400, 400);
//    } catch (SQLException e) {
//      log.error("Failed to connect to database: {}", e.getMessage(), e);
//    }
//
//    return  scene;
//  }
}
