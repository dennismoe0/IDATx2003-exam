package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.List;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * Custom container class for displaying menu options visually.
 */
public class MenuView {
  private Text title;
  private final BorderPane view;
  private VBox menu;
  private final List<MenuOption> options;

  /**
   * Default constructor for a MenuView.
   *
   * @param title   the title of the Menu to be displayed.
   * @param options A list of Menu options (buttons).
   */
  public MenuView(String title, List<MenuOption> options) {
    view = new BorderPane();
    this.title = new Text(title);
    this.options = options;
    createView();
    createView();
    view.setTop(this.title);
    view.setCenter(menu);
  }

  /**
   * For retrieving the parent object to change roots.
   *
   * @return returns the parent object of the Menu.
   */
  public Parent asParent() {
    return view;
  }

  private void createView() {
    menu = new VBox();
  }

  private void createAndConfigureMenu() {
    for (MenuOption option : options) {
      menu.getChildren().add(option);
    }
  }

}
