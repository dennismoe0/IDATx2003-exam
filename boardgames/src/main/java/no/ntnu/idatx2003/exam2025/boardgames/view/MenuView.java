package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


/**
 * Custom container class for displaying menu options visually.
 */
public class MenuView {
  private Label title;
  private StackPane root;
  private final BorderPane view;
  private VBox menu;
  private final List<MenuOption> options;
  private float width;
  private float height;
  private float borderWidth;

  /**
   * Default constructor for a MenuView.
   *
   * @param title   the title of the Menu to be displayed.
   * @param options A list of Menu options (buttons).
   */
  public MenuView(String title, List<MenuOption> options, float width, float height, float borderWidth) {
    root = new StackPane();
    view = new BorderPane();
    this.title = new Label(title);
    this.options = options;
    this.width = width;
    this.height = height;
    this.borderWidth = borderWidth;
    createView();
    createAndConfigureMenu();
    view.setCenter(menu);

  }

  /**
   * For retrieving the parent object to change roots.
   *
   * @return returns the parent object of the Menu.
   */
  public Parent asParent() {
    return root;
  }

  private void createView() {
    root.setPrefSize(width, height);
    Rectangle backgroundPane = new Rectangle(width, height);
    backgroundPane.getStyleClass().add("main-menu-background");
    root.getChildren().add(backgroundPane);
    root.getChildren().add(view);

    //refactor to make this section cleaner
    Region topSpacer = new Region();
    Region leftSpacer = new Region();
    Region bottomSpacer = new Region();
    Region rightSpacer = new Region();
    topSpacer.setPrefWidth(width);
    topSpacer.setPrefHeight(borderWidth);
    bottomSpacer.setPrefWidth(width);
    bottomSpacer.setPrefHeight(borderWidth);
    leftSpacer.setPrefWidth(borderWidth);
    leftSpacer.setPrefHeight(height);
    rightSpacer.setPrefWidth(borderWidth);
    rightSpacer.setPrefHeight(height);
    view.setTop(topSpacer);
    view.setBottom(bottomSpacer);
    view.setLeft(leftSpacer);
    view.setRight(rightSpacer);
    view.setMaxSize(width, height);

    title.getStyleClass().add("menu-title");

    menu = new VBox(2);
  }

  private void createAndConfigureMenu() {
    menu.getChildren().add(title);
    Rectangle spacer = new Rectangle(width - (2 * borderWidth), 10);
    spacer.getStyleClass().add("menu-spacer-bar");
    menu.getChildren().add(spacer);
    menu.getChildren().add(new ViewSpacer(width, 7f));

    for (MenuOption option : options) {
      menu.getChildren().add(option);
    }
  }

}
