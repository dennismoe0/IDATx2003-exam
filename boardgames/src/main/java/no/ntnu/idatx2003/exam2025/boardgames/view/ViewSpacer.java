package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.Region;

/**
 * View spacer for use in setting custom regions in UI design.
 */
public class ViewSpacer extends Region {
  /**
   * Default Spacer constructor.
   *
   * @param width  the intended width of the spacer object.
   * @param height the intended height of the spacer object.
   */
  public ViewSpacer(Float width, Float height) {
    super.setPrefWidth(width);
    super.setPrefHeight(height);
  }
}
