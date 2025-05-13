package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.control.Button;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.Command;

/**
 * An extension of the Button class used for constructing menus in the application.
 */
public class MenuOption extends Button {
  /**
   * Default constructor for a Menu option. Requires text that
   * can be displayed and a connection action to run.
   *
   * @param text   the text to be displayed as the menu option.
   * @param command the command to be performed when the menu option is clicked.
   */
  public MenuOption(String text, Command command, boolean uppercase) {
    if (uppercase) {
      text = text.toUpperCase();
    }
    super.setText(text);
    setOnAction(e -> command.execute());
    this.getStyleClass().add("menu-option");
  }
}
