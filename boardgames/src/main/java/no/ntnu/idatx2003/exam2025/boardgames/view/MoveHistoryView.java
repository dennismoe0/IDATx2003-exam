package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.HashMap;
import java.util.Map;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderGameMoveHistory;
import no.ntnu.idatx2003.exam2025.boardgames.util.LadderGameMessage;


/**
 * A view for tracking and displaying Move History.
 */
public class MoveHistoryView {
  private final StackPane root;
  private final ScrollPane scrollPane;
  private final VBox layoutBox;
  private final VBox messageBox;
  private final Map<LadderGameMessage, Label> storedMoves;
  private final LadderGameMoveHistory moveHistory;
  private final Label windowTitle;
  private final Rectangle background;

  /**
   * The default constructor for MoveHistoryView.
   *
   * @param moveHistory the Move History object to track.
   */
  public MoveHistoryView(LadderGameMoveHistory moveHistory) {
    root = new StackPane();
    messageBox = new VBox(5);
    storedMoves = new HashMap<>();
    layoutBox = new VBox(5);
    windowTitle = new Label("log: ");
    scrollPane = new ScrollPane(layoutBox);
    this.moveHistory = moveHistory;
    background = new Rectangle();
    root.getChildren().add(background);
    root.getChildren().add(scrollPane);

    configureViews();
    configureList();
    addStyling();
  }

  public Parent getRoot() {
    return root;
  }

  private void configureViews() {
    root.setMaxSize(400, 500);
    root.setPrefSize(400, 500);
    scrollPane.setMaxSize(300, 500);
    scrollPane.setPadding(new Insets(8));
    scrollPane.setPrefSize(380, 480);
    background.widthProperty().bind(root.widthProperty());
    background.heightProperty().bind(root.heightProperty());

    layoutBox.getChildren().add(windowTitle);
    layoutBox.getChildren().add(messageBox);
  }

  private void configureList() {
    moveHistory.getMessages().addListener((ListChangeListener<? super LadderGameMessage>) change -> {
      while (change.next()) {
        if (change.wasAdded()) {
          for (LadderGameMessage message : change.getAddedSubList()) {
            Label label = new Label(buildLabel(message));
            storedMoves.put(message, label);
            messageBox.getChildren().addFirst(label);
          }
        }
        if (change.wasRemoved()) {
          for (LadderGameMessage message : change.getRemoved()) {
            Label label = storedMoves.get(message);
            messageBox.getChildren().remove(label);
          }
        }
      }
    });
  }

  private void addStyling() {
    background.getStyleClass().add("inset-panel");
    scrollPane.getStyleClass().add("move-history");
    windowTitle.getStyleClass().add("subtle-text");

    scrollPane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> {
      Region viewport = (Region) scrollPane.lookup(".viewport");
      if (viewport != null) {
        viewport.setStyle("-fx-background-color: transparent;");
      }
    });
  }



  private String buildLabel(LadderGameMessage message) {
    return message.getPlayer().getPlayerName()
        + " rolled a "
        + message.getRoll()
        + " and moved from "
        + message.getStartTile()
        + " to "
        + message.getEndTile();
  }
}
