package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import no.ntnu.idatx2003.exam2025.boardgames.util.LadderGameMessage;

/**
 * Class for tracking the moves in the ladder game.
 */
public class LadderGameMoveHistory {
  private final ObservableList<LadderGameMessage> messages = FXCollections.observableArrayList();

  /**
   * A method for adding a new message to the move history.
   *
   * @param message a ladderGameMessage object.
   */
  public void addMessage(LadderGameMessage message) {
    messages.add(message);
    if (messages.size() >= 20) {
      messages.removeFirst();
    }
  }

  public ObservableList<LadderGameMessage> getMessages() {
    return messages;
  }
}
