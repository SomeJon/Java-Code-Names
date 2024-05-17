package engine.ui;

import engine.Definition;
import engine.Engine;
import engine.board.Board;
import engine.data.GameStatus;
import engine.data.Team;

public interface UiAction {

    public void buildStartingMenu(); //Builds start menu
    public Engine.eMenuAction openMenu();
    public String getXmlPath();
    public void exceptionHandler(Exception ReceivedError);
    public void showGameData(GameStatus ReceivedGameStatus);
    public void showBoard(Board ReceivedBoard, boolean i_Visible);
    public void showTurn(Team PlayingTeam, Board RecivedBoard);
    public Definition getDefinition();
    public void guessResult(String Received);
}
