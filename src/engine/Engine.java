package engine;

import engine.data.GameData;
import engine.response.LoadXmlResponse;
import engine.response.Response;
import ui.UiAction;
import jaxb.schema.FileReader;

import java.io.File;

public class Engine {
    public enum MenuAction {
        LOAD_XML,
        SHOW_GAME_DATA,
        START_GAME,
        PLAYER_TURN,
        GAME_STATUS,
        CLOSE
    }
    public enum eGuessResult{
        CorrectTeam,
        WrongTeam,
        BlackWord,
        NeutralWord
    }


    private final GameData Data;
    private final UiAction Ui;

    public Engine(UiAction i_UiLink) {
        Data = new GameData(i_UiLink);
        Ui = i_UiLink;
    }

    public void startGame(){
        MenuAction menuAction;
        Ui.buildStartingMenu();

        do {
            menuAction = Ui.openMenu();

            switch (menuAction) {
                case LOAD_XML:
                    loadXml();
            }

            Ui.showBoard(Data.getActiveData().getPlayingBoard(), false);
            Ui.showBoard(Data.getActiveData().getPlayingBoard(), true);

        }while(menuAction != MenuAction.CLOSE);
    }

    private void loadXml() {
        Ui.askForXml();
        LoadXmlResponse loadXml = new LoadXmlResponse();
        Ui.getResponse(loadXml);
        if(loadXml.receivedResponse()) {
            File responseFile = loadXml.getInputFile();
            try {
                FileReader.ReadXml(responseFile, Data);
                Data.startBoard(); //todo change this placemeant!
                Ui.updateBoard(Data.getActiveData().getPlayingBoard());
            } catch (Exception ignored) {
                System.out.println("Error reading xml file");
            }
        }
    }
}
