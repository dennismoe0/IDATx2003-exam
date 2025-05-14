package no.ntnu.idatx2003.exam2025.boardgames.util.command;

import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;

/**
 * A command used to change to a new screen from within the program.
 */
public class ChangeScreenCommand implements Command {
  private SceneRegister register;
  private SceneManager manager;
  private String sceneName;
  //Need a scene Manager.

  public ChangeScreenCommand(SceneRegister register, SceneManager manager, String input) {
    this.register = register;
    this.manager = manager;
    this.sceneName = input;
  }

  /**
   * A method for executing the given command and changing screens.
   */
  public void execute() {
    manager.changeRoot(register.get(sceneName));
  }
}
