package no.ntnu.idatx2003.exam2025.boardgames.util.command;

import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;

/**
 * A command used to change to a new screen from within the program.
 */
public class ChangeScreenCommand implements Command {
  private final SceneRegister register;
  private final SceneManager manager;
  private final String sceneKey;
  //Need a scene Manager.

  /**
   * Default constructor for a ChangeScreenCommand.
   *
   * @param register the Scene Register that calls a build view.
   * @param manager  the scene manager responsible for swapping scenes.
   * @param key      the String that will be checked in the register.
   */
  public ChangeScreenCommand(SceneRegister register, SceneManager manager, String key) {
    this.register = register;
    this.manager = manager;
    this.sceneKey = key;
  }

  /**
   * A method for executing the given command and changing screens.
   */
  public void execute() {
    manager.changeRoot(register.get(sceneKey));
  }
}
