package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Custom container class for displaying menu options visually.
 */
public class MenuView extends BorderPane {
  private MenuList menuList = new MenuList();
  private Text title;

  public MenuView(String title) {
    this.title = new Text(title);
    MenuView.super.setTop(this.title);
    MenuView.super.setCenter(menuList);
  }

  public void build() {

  }
}
