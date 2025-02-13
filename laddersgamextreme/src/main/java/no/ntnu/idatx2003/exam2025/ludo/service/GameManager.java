package no.ntnu.idatx2003.exam2025.ludo.service;

import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.Player;
import no.ntnu.idatx2003.exam2025.ludo.model.*;

import java.util.ArrayList;

public class GameManager {
  private ArrayList<Player> players;
  private Dice dice = new Dice();

  public void addPlayer(Player player) {
    players.add(player);
  }
  public void removePlayer(Player player) {
    players.remove(player);
  }
}
