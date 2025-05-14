package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TitledFieldView {
  private VBox root;
  private TextField field;
  private Label label;
  private String title;
  private String placeHolder;
  private float width;
  private float height;

  public TitledFieldView(String title, String placeHolder, float width, float height) {
    this.title = title;
    this.placeHolder = placeHolder;
    this.width = width;
    this.height = height;
    root = new VBox();
    configureField();
  }

  private void configureField() {
    label = new Label(title);
    field = new TextField();
    field.setMaxWidth(width);
    field.setMaxHeight(height);
    field.setPromptText(placeHolder);
    root.getChildren().addAll(label, field);
  }

  public VBox getRoot() {
    return root;
  }
}
