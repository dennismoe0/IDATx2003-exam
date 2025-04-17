package no.ntnu.idatx2003.exam2025.boardgames.model;

import org.slf4j.Logger;

import no.ntnu.idatx2003.exam2025.boardgames.util.Log;

public class Die {
  private static final Logger log = Log.get(Die.class);

  private final int sides;

  public Die(int sides) {
    if (sides < 2) {
      log.error("Invalid number of sides for die: {}", sides);
      throw new IllegalArgumentException("A die needs at least 2 sides...");
    }
    this.sides = sides;
    log.debug("Created die with {} sides", sides);
  }

}
