package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javafx.scene.Parent;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Scene Register used as a way of managing Scene changes.
 * Made to allow for easy swapping from the "Swap Scene" command.
 */
public class SceneRegister {
  private static final Logger logger = Log.get(SceneRegister.class);
  /*
  scene list:
  - main menu
  - Board game View
  - Adding players/choosing board/setting rules?
  - Settings
  - Stats
   */

  /*
   * The "Supplier(Parent)" form for integrating a factory
   * into the registry was suggested by ChatGPT.
   */
  private final Map<String, Supplier<Parent>> sceneMap = new HashMap<>();

  /**
   * Method for registering a new Scene with the Scene Registry.
   *
   * @param key      the king through which the factory can be called.
   * @param supplier A factory capable of building the intended scene.
   */
  public void register(String key, Supplier<Parent> supplier) {
    logger.info("Registering " + key + " with " + supplier.toString());
    sceneMap.put(key, supplier);
  }

  /**
   * Method for retrieving a factory method that supplies the requested view.
   *
   * @param key A string representing the "name" of the intended scene.
   * @return returns a Parent node object constructed by the called factory.
   */
  public Parent get(String key) {
    logger.info("Getting key: [" + key + "] from SceneRegister");
    if (!sceneMap.containsKey(key)) {
      throw new IllegalArgumentException("Key " + key + " not found");
    }
    return sceneMap.get(key).get();
  }
}
