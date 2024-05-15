package ui;

import menu.console.MainMenu;

public class UiData {
    private MainMenu mainMenu;
    private int cardSize;

    public int getCardSize() {
        return cardSize;
    }

    public void setCardSize(int cardSize) {
        this.cardSize = cardSize;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public UiData() {
        mainMenu = new MainMenu("Code Names: Main Menu");
    }
}
