package no.ntnu.idatx2003.exam2025.boardgames.util.command;

import javafx.scene.Node;
import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;
import no.ntnu.idatx2003.exam2025.boardgames.util.ViewFactory;

/**
 * A command used to change to a new screen from within the program.
 */
public class ChangeScreenCommand implements Command {
  private SceneRegister register;
  private SceneManager manager;
  private GameSession session;
  private ViewFactory viewFactory;
  private String sceneName;
  //Need a scene Manager.

  public ChangeScreenCommand(SceneRegister register, SceneManager manager, GameSession session, String sceneName) {
    this.register = register;
    viewFactory = new ViewFactory();
  }

  /**
   * A method for executing the given command and changing screens.
   */
  public void execute() {

  }
}
