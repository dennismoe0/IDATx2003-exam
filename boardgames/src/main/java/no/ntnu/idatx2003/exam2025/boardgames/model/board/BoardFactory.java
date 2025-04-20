package no.ntnu.idatx2003.exam2025.boardgames.model.board;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.EmptyTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

public class BoardFactory {
  private Board board;

  public Board createDefaultLadderBoard(){
    board = new Board();
    Tile tile;
    //initialize the tiles
    //When reading from an XML we'll break the initialize and assemble into separate steps.
    for (int i = 0; i < 90; i++) {
      //switch statement to handle assigning tile functions.
      switch (i){
        case 0:


        default:
          tile = new Tile(i,new EmptyTileStrategy());
          break;
      }
      board.setTile(i, tile);
    }


    return board;
  }
}
