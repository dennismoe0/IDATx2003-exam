package no.ntnu.idatx2003.exam2025.boardgames.util;

import com.google.gson.JsonObject;

import java.io.IOException;

/**
 * Reads JSON info from a file and converts it to Board information.
 */
public interface JsonFileReader {
  /**
   * Reads the info from a JSON file and reports it.
   *
   * @param path
   * @return
   */
  public JsonObject readJson(String path) throws IOException;
}
