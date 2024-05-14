package ui;

import engine.Definition;
import engine.board.Board;
import engine.data.GameStatus;
import engine.data.Team;
import menu.console.ChoiceNotifier;
import menu.console.MainMenu;
import menu.console.Menu;
import engine.Engine.eMenuAction;
import menu.console.MenuItem;

public class UiAction implements engine.ui.UiAction, ChoiceNotifier {
    private UiData Data;
    private eMenuAction CurrentChoice;

    public static void main(String[] args) {
        UiAction uiAction = new UiAction();
        uiAction.buildStartingMenu();
        uiAction.openMenu();
    }

    public UiAction() {
        Data = new UiData();
    }

    @Override
    public void buildStartingMenu() {
        Menu StartMenu = Data.getMainMenu().getStartMenu();
        StartMenu.createMenuOption("Load data from an xml file", eMenuAction.LoadXml, this);
    }

    @Override
    public eMenuAction openMenu() {
        MainMenu MainMenu = Data.getMainMenu();

        MainMenu.play();
        if(MainMenu.isClosing())
            CurrentChoice = eMenuAction.Close;

        return CurrentChoice;
    }

    @Override
    public String getXmlPath() {
        return "";
    }

    @Override
    public void exceptionHandler(Exception ReceivedError) {

    }

    @Override
    public void showGameData(GameStatus ReceivedGameStatus) {

    }

    @Override
    public void showBoard(Board ReceivedBoard) {

    }

    @Override
    public void showTurn(Team PlayingTeam, Board RecivedBoard) {

    }

    @Override
    public Definition getDefinition() {
        return null;
    }

    @Override
    public void guessResult(String Received) {

    }

    @Override
    public void Notify(Object sender) {
        eMenuAction action = (eMenuAction) ((MenuItem)sender).getItemValue();
        if (action == eMenuAction.LoadXml) {
            CurrentChoice = action;
            Data.getMainMenu().pauseRunning();
        }
    }
}
