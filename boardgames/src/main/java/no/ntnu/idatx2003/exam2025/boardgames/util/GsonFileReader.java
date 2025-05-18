package no.ntnu.idatx2003.exam2025.boardgames.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A reader for parsing json objects from a Json file, uses gson formatting.
 */
public class GsonFileReader implements JsonFileReader {

  /**
   * Main method for reading a json file.
   *
   * @param path the path to the specified file.
   * @return returns the entire file as a JsonObject
   * @throws IOException can throw an error for a missing or empty file.
   */
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
