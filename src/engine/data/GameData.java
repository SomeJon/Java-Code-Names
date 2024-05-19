package engine.data;

import engine.board.Board;
import engine.board.card.CardGroup;
import engine.board.card.GroupTeam;
import engine.exception.loadxml.OutOfBoundLoad;
import engine.ui.UiAction;

import java.util.ArrayList;
import java.util.Set;

import static engine.board.Board.buildBoard;

public class GameData {
    private final UiAction UserInterface;
    private Set<String> Words = null;
    private Set<String> BlackWords = null;
    private int NumOfColumns;
    private int NumOfRows;
    private ActiveGame ActiveData = null;
    private GameStatus Status = null;

    public UiAction getUiAction() {
        return UserInterface;
    }

    public GameData(UiAction i_UserInterface) {
        UserInterface = i_UserInterface;
    }

    public ActiveGame getActiveData() {
        return ActiveData;
    }

    public GameStatus getStatus() {
        return Status;
    }

    public void loadData
            (GameStatus i_Status, Integer i_NumOfColumns, Integer i_NumOfRows,
             Set<String> i_Words, Set<String> i_BlackWords) {
        if(i_NumOfColumns * i_NumOfRows >= i_Status.getNumOfBlackCards() + i_Status.getNumOfWords()) {
            throw  new OutOfBoundLoad("Matrix Size",
                    i_Status.getNumOfBlackCards() + i_Status.getNumOfWords(),
                    i_NumOfColumns * i_NumOfRows, 0);
        }

        Status = i_Status;
        NumOfColumns = i_NumOfColumns;
        NumOfRows = i_NumOfRows;
        BlackWords = i_BlackWords;
        Words = i_Words;
    }

    public void startBoard() {
        Board newBoard = buildBoard(new ArrayList<>(Words), new ArrayList<>(BlackWords),
                Status, NumOfRows, NumOfColumns);
        GroupTeam team = newBoard.getGroupTeams().get(0);
        ActiveData = new ActiveGame(newBoard, team);
    }
}
