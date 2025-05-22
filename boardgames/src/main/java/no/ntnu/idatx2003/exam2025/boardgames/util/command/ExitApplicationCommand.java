package no.ntnu.idatx2003.exam2025.boardgames.util.command;

import javafx.application.Platform;

/**
 * Simple command used for triggering end of program operations.
 */
public class ExitApplicationCommand implements Command {

  @Override
  public void execute() {
    Platform.exit();
  }
}
