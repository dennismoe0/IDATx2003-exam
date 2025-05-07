package no.ntnu.idatx2003.exam2025.boardgames.util.command;

import no.ntnu.idatx2003.exam2025.boardgames.controller.DiceController;

/**
 * A command for interfacing with the Dice of a board.
 */
public class RollDiceCommand implements Command {
  private final DiceController controller;

  /**
   * The default constructor. Calls roll-dice command.
   *
   * @param controller The Dice Controller for the current board game.
   */
  public RollDiceCommand(DiceController controller) {
    this.controller = controller;
  }

  /**
   * Execute function used to perform whatever command needs to be performed.
   */
  @Override
  public void execute() {
    controller.rollDice();
  }
}
