package no.ntnu.idatx2003.exam2025.boardgames.controller;

import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

import java.util.List;

public class PlayerListViewController {
  //needs reference to the DAO for loading saved players.
  //needs methods for populating the list view I guess?
  private final PlayerDaoImpl playerDAO;
  private final List<Player> playerList;

  public PlayerListViewController() {

  }
}
