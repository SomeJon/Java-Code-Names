package engine;

import engine.board.card.Card;
import engine.data.GameData;
import jaxb.schema.FileReader;
import ui.UiAction;

public class Engine {
    public enum eMenuAction {
        LoadXml,
        ShowGameData,
        StartGame,
        PlayTurn,
        GameStatus,
        Close
    }
    public enum eGuessResult{
        CorrectTeam,
        WrongTeam,
        BlackWord,
        NeutralWord
    }


    private final GameData Data;


    public static void main(String[] args) {
        Engine engine = new Engine(new ui.UiAction());
        engine.loadXml("C:\\Users\\bhbha\\IdeaProjects\\JavaCodeNames\\src\\resource\\classic.xml");
        engine.Data.startBoard();
        System.out.println("ehhhh");
    }

    public Engine(UiAction UiLinker) {
        Data = new GameData(UiLinker);
    }

    public void startEngine(){
        loadXml("C:\\Users\\bhbha\\IdeaProjects\\JavaCodeNames\\src\\resource\\classic.xml");
        Data.startBoard();
        Data.ui().showBoard(Data.getActiveData().getPlayingBoard(), false);
        Data.ui().showBoard(Data.getActiveData().getPlayingBoard(), true);

    }

    private void loadXml(String filePath) {
        try {
            FileReader.ReadXml(filePath, Data);
        }
        catch (Exception ignored) {
            System.out.println("Error reading xml file");
        }
    }
}
