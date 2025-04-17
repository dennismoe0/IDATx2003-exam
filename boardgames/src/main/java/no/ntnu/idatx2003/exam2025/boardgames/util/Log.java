package no.ntnu.idatx2003.exam2025.boardgames.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for creating logger instances for different classes.
 */
public class Log {
  private Log() {
  }

  public static Logger get(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }
}
