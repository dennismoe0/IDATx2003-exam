package no.ntnu.idatx2003.exam2025.boardgames.util.command;

import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;

/**
 * A simple command used to display an alert, useful for user feedback.
 */
public class ShowAlertCommand implements Command {
  private final String alertTitle;
  private final String alertMessage;

  /**
   * Constructor for a show alert command.
   *
   * @param alertTitle   string representing the title of the alert.
   * @param alertMessage the string message to be displayed.
   */
  public ShowAlertCommand(String alertTitle, String alertMessage) {
    this.alertTitle = alertTitle;
    this.alertMessage = alertMessage;
  }

  @Override
  public void execute() {
    AlertUtil.showInfo(alertTitle, alertMessage);
  }
}
