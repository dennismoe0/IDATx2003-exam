package no.ntnu.idatx2003.exam2025.boardgames.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class GsonFileReader implements JsonFileReader {

  public JsonObject readJson(String path) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(path));
    StringBuilder json = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      json.append(line);
    }
    String jsonString = json.toString();
    reader.close();
    return JsonParser.parseString(jsonString).getAsJsonObject();
  }
}
