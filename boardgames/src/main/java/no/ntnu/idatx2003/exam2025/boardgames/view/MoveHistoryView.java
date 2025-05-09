package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.collections.ListChangeListener;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderGameMoveHistory;
import no.ntnu.idatx2003.exam2025.boardgames.util.LadderGameMessage;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.TempMoveMessageCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * A view for tracking and displaying Move History.
 */
public class MoveHistoryView {
  private final VBox root;
  private final LadderGameMoveHistory moveHistory;
  private final ScrollPane scrollPane;
  private Map<LadderGameMessage, Label> storedMoves;

  private final VBox realRoot;
  private Button button;
  private TempMoveMessageCommand tempMoveMessageCommand;

  /**
   * The default constructor for MoveHistoryView.
   *
   * @param moveHistory the Move History object to track.
   */
  public MoveHistoryView(LadderGameMoveHistory moveHistory) {
    root = new VBox(5);
    this.moveHistory = moveHistory;
    storedMoves = new HashMap<>();
    scrollPane = new ScrollPane(root);
    scrollPane.setPrefHeight(200);

    realRoot = new VBox(5);
    realRoot.getChildren().add(scrollPane);
    tempMoveMessageCommand = new TempMoveMessageCommand(moveHistory);

    button = new Button("Add New Message");
    button.setOnAction(event -> tempMoveMessageCommand.execute());
    realRoot.getChildren().add(button);
    realRoot.setPrefSize(400, 500);

    moveHistory.getMessages().addListener((ListChangeListener<? super LadderGameMessage>) change -> {
      while (change.next()) {
        if (change.wasAdded()) {
          for (LadderGameMessage message : change.getAddedSubList()) {
            Label label = new Label(buildLabel(message));
            storedMoves.put(message, label);
            root.getChildren().addFirst(label);
          }
        }
        if (change.wasRemoved()) {
          for (LadderGameMessage message : change.getRemoved()) {
            Label label = storedMoves.get(message);
            root.getChildren().remove(label);
          }
        }
      }
    });
  }

  public Parent getRoot() {
    return realRoot;
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
