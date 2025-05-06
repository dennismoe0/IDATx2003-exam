package no.ntnu.idatx2003.exam2025.boardgames;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.PrintLineCommand;
import no.ntnu.idatx2003.exam2025.boardgames.view.MenuOption;
import no.ntnu.idatx2003.exam2025.boardgames.view.MenuView;


/**
 * The main Application File for starting and stopping the program.
 */
public class BoardGameApplication extends Application {


  @Override
  public void start(Stage primaryStage) throws Exception {
    MenuView view = new MenuView("Main Menu", buildTestMenu());

    Scene scene = new Scene(view.asParent(), 400, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * The main method for running the program.
   *
   * @param args default args.
   */
  public static void main(String[] args) {
    launch(args);
  }

  private List<MenuOption> buildTestMenu() {
    List<MenuOption> menuOptions = new ArrayList<>();
    menuOptions.add(new MenuOption("Start", new PrintLineCommand("Start")));
    menuOptions.add(new MenuOption("Settings", new PrintLineCommand("Settings")));
    menuOptions.add(new MenuOption("Players", new PrintLineCommand("Players")));
    menuOptions.add(new MenuOption("Exit", new PrintLineCommand("Exit")));
    return menuOptions;
  }
}
