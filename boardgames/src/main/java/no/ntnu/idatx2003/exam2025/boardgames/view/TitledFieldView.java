package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.geometry.Pos;
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
    root = new VBox(1);
    configureField();
    assignStyling();
  }

  private void configureField() {
    label = new Label(title);
    field = new TextField();
    field.setPromptText(placeHolder);
    root.getChildren().addAll(label, field);
    root.setMaxSize(width, height);
    root.setAlignment(Pos.CENTER);
  }

  private void assignStyling() {
    label.getStyleClass().add("h2");
  }

  public VBox getRoot() {
    return root;
  }

  public String getFieldText() {
    return field.getText();
  }
}
