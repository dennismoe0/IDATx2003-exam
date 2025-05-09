package no.ntnu.idatx2003.exam2025.boardgames.util.command;

import java.util.ArrayList;
import java.util.Random;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderGameMoveHistory;
import no.ntnu.idatx2003.exam2025.boardgames.util.LadderGameMessage;


public class TempMoveMessageCommand implements Command {
  private LadderGameMoveHistory history;
  private ArrayList<Player> players;
  private final Random random = new Random();

  public TempMoveMessageCommand(LadderGameMoveHistory history) {
    this.history = history;
    players = new ArrayList<>();
    Player player1 = new Player(1, "Sasha", 27);
    players.add(player1);
    Player player2 = new Player(2, "Dennis", 24);
    players.add(player2);
  }

  /**
   * Execute function used to perform whatever command needs to be performed.
   */
  @Override
  public void execute() {
    history.addMessage(generateMessage());
  }

  private LadderGameMessage generateMessage() {
    LadderGameMessage message = new LadderGameMessage(getPlayer(), random.nextInt(10),
        random.nextInt(20), random.nextInt(30));
    return message;
  }

  private Player getPlayer() {
    return players.get(random.nextInt(players.size()));
  }


}
