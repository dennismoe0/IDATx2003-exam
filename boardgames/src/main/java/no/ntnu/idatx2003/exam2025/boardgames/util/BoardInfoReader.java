package no.ntnu.idatx2003.exam2025.boardgames.util;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardInfo;

/**
 * Class for reading boardinfo objects to be used for building a UI.
 */
public class BoardInfoReader {
  private final GsonFileReader reader;
  private final String fileUrl;
  private final List<BoardInfo> boardInfoList;

  /**
   * Default constructor for the Board Info Reader.
   *
   * @param url the url to the file you want to load. Should link to the main board directory.
   */
  public BoardInfoReader(String url) {
    reader = new GsonFileReader();
    fileUrl = url;
    boardInfoList = new ArrayList<BoardInfo>();
    buildList();
  }

  /**
   * Method for retrieving all found boards.
   *
   * @return returns a list of BoardInfo objects.
   */
  public List<BoardInfo> getAllBoards() {
    if (!boardInfoList.isEmpty()) {
      return boardInfoList;
    } else {
      return null;
    }
  }

  /**
   * Method for retrieving a list of boards filtered by game name.
   *
   * @param input requires a string representing the game name.
   * @return returns a list of boards for that game.
   */
  public List<BoardInfo> getBoards(String input) {
    if (boardInfoList.isEmpty()) {
      return null;
    }
    return boardInfoList.stream().filter(
        b -> Objects.equals(b.getGame(), input)).toList();
  }

  private void buildList() {
    JsonArray boardList = readJson();
    boardInfoList.clear();
    String name = "";
    String game = "";
    String url = "";
    if (boardList != null) {
      for (int i = 0; i < boardList.size(); i++) {
        boardInfoList.add(new BoardInfo());
        JsonObject board = boardList.get(i).getAsJsonObject();
        name = board.get("name").getAsString();
        game = board.get("game").getAsString();
        url = board.get("url").getAsString();
        boardInfoList.get(i).setName(name);
        boardInfoList.get(i).setGame(game);
        boardInfoList.get(i).setUrl(url);
      }
    }
  }

  private JsonArray readJson() {
    try {
      return reader.readJson(fileUrl).getAsJsonArray();
    } catch (IOException e) {
      return null;
    }
  }

}
