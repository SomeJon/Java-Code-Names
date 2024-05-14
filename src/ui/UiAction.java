package ui;

import engine.Definition;
import engine.Engine;
import engine.board.Board;
import engine.data.GameStatus;
import engine.data.Team;
import menu.console.ChoiceNotifier;
import menu.console.Menu;
import engine.Engine.eMenuAction;
import menu.console.MenuItem;

public class UiAction implements engine.ui.UiAction, ChoiceNotifier {
    private enum eOption{
        LoadData,

    }

    private UiData Data;
    private eOption CurrentChoice;

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
        this.Data.getMainMenu().play();
        return null;
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
            System.out.println("Load data from an xml file");
        }
    }
}
