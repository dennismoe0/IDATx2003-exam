package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class PlayerListView {
  private final List<PlayerItemView> playerItemViews;
  private final VBox layout;


  public PlayerListView() {
    playerItemViews = new ArrayList<>();
    layout = new VBox(2);
  }
}
