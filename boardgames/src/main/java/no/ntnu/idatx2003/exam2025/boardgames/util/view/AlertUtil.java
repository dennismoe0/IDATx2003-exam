package no.ntnu.idatx2003.exam2025.boardgames.util.view;

import javafx.scene.control.Alert;

/**
 * Utility class for showing information and error alerts using JavaFX.
 * Nice for popups that need to be displayed.
 */
public class AlertUtil {

  /**
   * Shows an information alert dialog with the given title and message.
   *
   * @param title   the title of the alert dialog
   * @param message the message to display in the alert dialog
   */
  public static void showInfo(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Shows an error alert dialog with the given title and message.
   *
   * @param title   the title of the alert dialog
   * @param message the message to display in the alert dialog
   */
  public static void showError(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}