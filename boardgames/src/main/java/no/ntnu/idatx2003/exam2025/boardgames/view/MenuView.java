package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Custom container class for displaying menu options visually.
 */
public class MenuView {
  private Text title;
  private BorderPane view;
  private VBox menu;
  private List<MenuOption> options;


  public MenuView(String title, List<MenuOption> options) {
    view = new BorderPane();
    this.title = new Text(title);
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
