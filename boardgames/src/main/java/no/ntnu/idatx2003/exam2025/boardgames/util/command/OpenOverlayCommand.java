package no.ntnu.idatx2003.exam2025.boardgames.util.command;

import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;

/**
 * Command for opening an overlay scene.
 */
public class OpenOverlayCommand implements Command {
  private final SceneRegister register;
  private final SceneManager manager;
  private final String sceneKey;

  /**
   * Default constructor for overlay command.
   *
   * @param register a register containing listed scenes.
   * @param manager  scene manager for opening the overlay.
   * @param sceneKey string key for representing scene to open.
   */
  public OpenOverlayCommand(SceneRegister register, SceneManager manager, String sceneKey) {
    this.register = register;
    this.manager = manager;
    this.sceneKey = sceneKey;
  }

  /**
   * Execute function used to perform whatever command needs to be performed.
   */
  @Override
  public void execute() {
    manager.showOverlay(register.get(sceneKey));
  }
}
