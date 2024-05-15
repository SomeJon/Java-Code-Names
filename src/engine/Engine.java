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
        printCards(engine.Data.getActiveData().getPlayingBoard().getBoard());
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
            System.out.println("Error reading xml file");
        }
    }

    public static void printCards(Card[][] cards) {
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards[i].length; j++) {
                System.out.printf("Card[%d][%d] - Text: %s, ID: %d Group: %s \n",
                        i, j, cards[i][j].getText(), cards[i][j].getID(), cards[i][j].getGroup().getName());
            }
        }
    }
}
