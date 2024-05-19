package engine.ui;

import engine.Engine;
import engine.board.Board;
import engine.board.card.GroupTeam;
import engine.data.GameStatus;
import engine.data.Identification;
import engine.exception.CodeNameExceptions;
import engine.response.Response;

public interface UiAction {

    public void buildStartingMenu(); //Builds start menu
    public Engine.MenuAction openMenu();
    public void showBoard(Board ReceivedBoard, boolean i_Visible);
    public void getResponse(Response o_Response);
    public void askForXml();
    public void updateBoard(Board i_ReceivedBoard);
    public void addFileData();
    public void exceptionHandler(CodeNameExceptions i_ReceivedError);
    public void showGameDetails(GameStatus i_ReceivedGameStatus);
    public void showTeam(GroupTeam PlayingTeam);
    public void showIdentification(Identification i_CurrentIdentification);
//    public void guessResult(String Received);
}
