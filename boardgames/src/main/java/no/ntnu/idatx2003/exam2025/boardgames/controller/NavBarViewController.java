package no.ntnu.idatx2003.exam2025.boardgames.controller;

import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.ChangeScreenCommand;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.ShowAlertCommand;

public class NavBarViewController {
  private SceneRegister register;
  private SceneManager sceneManager;
  private ChangeScreenCommand changeScreenCommand;
  private ShowAlertCommand showSettingsAlertCommand;
  private ShowAlertCommand showInfoAlertCommand;


  public NavBarViewController(SceneRegister register, SceneManager sceneManager) {
    this.register = register;
    this.sceneManager = sceneManager;
    changeScreenCommand = new ChangeScreenCommand(register, sceneManager, "mainMenu");
  }

  public void showSettingsAlert() {

  }
}
