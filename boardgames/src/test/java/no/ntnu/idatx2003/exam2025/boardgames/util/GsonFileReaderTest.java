package no.ntnu.idatx2003.exam2025.boardgames.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GsonFileReaderTest {

  GsonFileReader reader;
  String filePath = ("src/main/resources/assets/boards/laddergameboards/laddergame_classic.json");

  @Test
  void readJson() {
    reader = new GsonFileReader();
    JsonObject jsonObject = null;
    try{
     jsonObject = reader.readJson(filePath);
    }
    catch (IOException e) {
      System.out.println("Error Encountered: " + e.getMessage());
    }
    if(jsonObject != null) {
      StringBuilder readJson = new StringBuilder();
      String description = jsonObject.get("description").getAsString();
      String name = jsonObject.get("name").getAsString();
      int boardSize = jsonObject.get("board-size").getAsInt();

      readJson.append(name);
      readJson.append("\n");
      readJson.append(description);
      readJson.append("\n");
      readJson.append(boardSize);

      assertNotNull(description);
      assertEquals("Snakes 'n Ladders (90)", name);
      System.out.println(readJson.toString());
    }


  }
}