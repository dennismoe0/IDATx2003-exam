package no.ntnu.idatx2003.exam2025.boardgames.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for creating logger instances for different classes.
 */
public class Log {
  private Log() {
  }

  /**
   * A logger object for logging program lifecycle.
   *
   * @param clazz the class in which the logger object is built in.
   * @return returns a Logger object from a LoggerFactory.
   */
  public static Logger get(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }
}
