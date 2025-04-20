package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardGame {
  private Board board;
  private String name;
  private List<Player> playerList = new ArrayList<Player>();

  public Board getBoard(){
    return board;
  }
  public void setBoard(Board board){
    this.board = board;
  }
  public String getName(){
    return name;
  }
  public void setName(String name){
    this.name = name;
  }
}
