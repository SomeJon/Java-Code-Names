package engine;

import engine.board.Board;
import engine.board.card.GroupTeam;
import engine.data.GameData;
import engine.data.Identification;
import engine.exception.CodeNameExceptions;
import engine.exception.turn.IdentificationException;
import engine.response.IdentificationResponse;
import engine.response.LoadXmlResponse;
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
                    break;
                case SHOW_GAME_DATA:
                    Ui.showGameDetails(Data.getStatus());
                    break;
                case START_GAME:
                    Data.startBoard();
                    Ui.updateBoard(Data.getActiveData().getPlayingBoard());
                    break;
                case PLAYER_TURN:
                    playTurn();
                    break;
            }

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
                Ui.addFileData();
            } catch (CodeNameExceptions e) {
                System.out.println("Error reading xml file");
            } catch (Exception ignore) {}
        }
    }

    public void playTurn(){
        boolean loopContinue;
        IdentificationResponse response;
        GroupTeam playingTeam = Data.getActiveData().getPlayingTeamGreoup();
        Board playingBoard = Data.getActiveData().getPlayingBoard();
        Identification currentIdentification = null;
                
        Ui.showTeam(playingTeam);
        Ui.showBoard(playingBoard, true);

        do {
            response = new IdentificationResponse();
            Ui.getResponse(response);
            if(response.getRelated() >
                    playingTeam.getCards() - playingTeam.getCardsFlipped() || response.getRelated() < 1) {
                loopContinue = true;
                Ui.exceptionHandler(new IdentificationException("Related words", response.getRelated(),
                playingTeam.getCards() - playingTeam.getCardsFlipped(), 1));
            }
            else{
                loopContinue = false;
                currentIdentification = new Identification(response.getIdentification(), response.getRelated());
            }
        }while(loopContinue);
        
        Ui.showTeam(playingTeam);
        Ui.showBoard(playingBoard, false);
        Ui.showIdentification(currentIdentification);
        do{

        }while(loopContinue);
    }
}
