package ui;

import menu.console.MainMenu;

public class UiData {
    private MainMenu mainMenu;

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public UiData() {
        mainMenu = new MainMenu("Code Names: Main Menu");
    }
}
