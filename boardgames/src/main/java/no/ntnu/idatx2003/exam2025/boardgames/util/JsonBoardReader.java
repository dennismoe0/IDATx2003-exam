package no.ntnu.idatx2003.exam2025.boardgames.util;

import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;

/**
 * Reads JSON info from a file and converts it to Board information.
 */
public interface JsonBoardReader {
  /**
   * Reads the info from a JSON file and reports it.
   *
   * @param path
   * @return
   */
  Board readBoard(String path);
}
