package engine;

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
        System.out.println("ehhhh");
    }

    public Engine(UiAction UiLinker) {
        Data = new GameData(UiLinker);
    }

    public void startEngine(){

    }

    private void loadXml(String filePath) {
        try {
            FileReader.ReadXml(filePath, Data);
        }
        catch (Exception ignored) {

        }
    }
}
