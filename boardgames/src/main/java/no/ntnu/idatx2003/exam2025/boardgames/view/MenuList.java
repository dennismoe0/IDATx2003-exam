package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.ArrayList;
import java.util.List;

public class MenuList{
  private List<MenuOption> options;

  public MenuList() {
    options = new ArrayList<MenuOption>();
  }
  public void addOption(MenuOption option) {
    options.add(option);
  }
  public List<MenuOption> getOptions() {
    return options;
  }
  public void removeOption(MenuOption option) {
    options.remove(option);
  }



}
