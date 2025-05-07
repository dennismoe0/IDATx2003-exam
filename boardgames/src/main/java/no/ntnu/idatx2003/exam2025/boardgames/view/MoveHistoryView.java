package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderGameMoveHistory;
import no.ntnu.idatx2003.exam2025.boardgames.util.LadderGameMessage;

/**
 * A view for tracking and displaying Move History.
 */
public class MoveHistoryView {
  private final VBox root;
  LadderGameMoveHistory moveHistory;
  ScrollPane scrollPane;

  /**
   * The default constructor for MoveHistoryView.
   *
   * @param moveHistory the Move History object to track.
   */
  public MoveHistoryView(LadderGameMoveHistory moveHistory) {
    root = new VBox(5);
    this.moveHistory = moveHistory;
    scrollPane = new ScrollPane(root);

    moveHistory.getMessages().addListener((ListChangeListener<? super LadderGameMessage>) change -> {
      while (change.next()) {
        if (change.wasAdded()) {
          for (LadderGameMessage message : change.getAddedSubList()) {
            root.getChildren().addFirst(new Label(buildLabel(message)));
          }
        }
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
