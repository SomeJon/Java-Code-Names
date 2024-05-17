package engine.data;

import engine.board.Board;
import engine.ui.UiAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public UiAction ui() {
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
             Set<String> i_Words, Set<String> i_BlackWords) { // todo: check inputs and throw to hell if its wrong
        if(i_NumOfColumns * i_NumOfRows >= i_Status.getNumOfBlackCards() + i_Status.getNumOfWords()) {
            //todo throw
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
        Team team = Status.getTeams().get(0);
        ActiveData = new ActiveGame(newBoard, team);
    }
}
