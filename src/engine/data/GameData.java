package engine.data;

import engine.ui.UiAction;

import java.util.Set;

public class GameData {
    private final UiAction UserInterface;
    private Set<String> Words = null;
    private Set<String> BlackWords = null;
    private Integer NumOfRows = null;
    private Integer NumOfColumns = null;
    private Active ActiveData = null;
    private GameStatus Status = null;

    public GameData
            (GameStatus status, UiAction userInterface,
             Integer numOfColumns, Integer numOfRows,
             Set<String> blackWords, Set<String> words) {
        Status = status;
        UserInterface = userInterface;
        NumOfColumns = numOfColumns;
        NumOfRows = numOfRows;
        BlackWords = blackWords;
        Words = words;
    }

    public GameData(UiAction userInterface) {
        UserInterface = userInterface;
    }

    public void loadData
            (GameStatus status, Integer numOfColumns, Integer numOfRows,
             Set<String> words, Set<String> blackWords) { // todo: check inputs and throw to hell if its wrong
        Status = status;
        NumOfColumns = numOfColumns;
        NumOfRows = numOfRows;
        BlackWords = blackWords;
        Words = words;
    }
}
