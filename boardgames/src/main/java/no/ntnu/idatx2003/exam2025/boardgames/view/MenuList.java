package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.VBox;

/**
 * A class for managing the list of a Menu View.
 */
public class MenuList extends VBox {
  private final List<MenuOption> options = new ArrayList<MenuOption>();

  /**
   * Method for adding menu options to list.
   *
   * @param option An option to be added to the menu view.
   */
  public void add(MenuOption option) {
    options.add(option);
  }

  /**
   * A method for clearing a menu.
   */
  public void clear() {
    options.clear();
  }
}
