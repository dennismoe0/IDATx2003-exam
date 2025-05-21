package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class InsetPanel extends StackPane {
  private Rectangle background;
  private float width;
  private float height;

  public InsetPanel(float width, float height) {
    this.width = width;
    this.height = height;

    background = new Rectangle();
    configureViews();
    assignStyling();
  }

  public Parent getRoot() {
    return super.getParent();
  }

  private void configureViews() {
    super.setMaxSize(width, height);
    super.setPrefHeight(100);
    background.setWidth(width);
    background.setHeight(height);

    super.getChildren().add(background);
  }

  private void assignStyling() {
    background.getStyleClass().add("inset-panel");
  }
}
