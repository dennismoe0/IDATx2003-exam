package no.ntnu.idatx2003.exam2025.boardgames.util.command;

/**
 * A basic command used for testing UI implementaiton.
 */
public class PrintLineCommand implements Command {
  private final String line;

  /**
   * A basic constructor for the PrintLine command.
   *
   * @param line a String representing the line to be printed.
   */
  public PrintLineCommand(String line) {
    this.line = line;
  }

  /**
   * Execute function used to perform whatever command needs to be performed.
   */
  @Override
  public void execute() {
    System.out.println(line);
  }
}
