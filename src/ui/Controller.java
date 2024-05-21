package ui;

import dto.Dto;
import dto.type.board.DtoBoard;
import dto.type.data.DtoGameDetails;
import dto.type.data.DtoGameEndResult;
import dto.type.data.DtoGuessResult;
import dto.type.data.DtoGroupTeam;
import engine.Engine;
import engine.EngineInterface;
import engine.board.card.GroupTeam;
import engine.data.Identification;
import engine.exception.CodeNameExceptions;
import engine.exception.turn.CardFlippedException;
import engine.exception.turn.IdentificationException;
import engine.response.GuesserResponse;
import engine.response.IdentificationResponse;
import engine.response.LoadXmlResponse;
import engine.response.Response;
import ui.interfaces.UiViewInterface;

import java.security.acl.Group;

public class Controller {
    public static int EndGuessId = 0;
    private final UiViewInterface Ui;
    private final EngineInterface Engine;

    public void Start(){
        MenuAction menuAction;
        Ui.buildStartingMenu();

        do {
            menuAction = Ui.openMenu();

            switch (menuAction) {
                case LOAD_XML:
                    loadXml();
                    break;
                case SHOW_GAME_DATA:
                    showGameData();
                    break;
                case START_GAME:
                    startGameInstance();
                    break;
                case PLAYER_TURN:
                    playTurn();
                    break;
                case GAME_STATUS:
                    gameStatus();
            }

        }while(menuAction != MenuAction.CLOSE);
    }

    public Controller(UiViewInterface view, EngineInterface engine) {
        Ui = view;
        Engine = engine;
    }

    private void loadXml() {
        Ui.askForXml();
        Response xmlResponse = new LoadXmlResponse();
        Ui.getResponse(xmlResponse);

        if(xmlResponse.receivedResponse()){
            try {
                Engine.loadXml(xmlResponse);
                Ui.addFileData();
            } catch (CodeNameExceptions e) {
                Ui.exceptionHandler(e, false);
            } catch (Exception ignore) {}
        }
    }

    private void showGameData(){
        DtoGameDetails status = (DtoGameDetails)Engine.getStatus();
        Ui.showGameDetails(status);
    }

    private void startGameInstance(){
        Engine.startGame();
        DtoBoard Board = (DtoBoard)Engine.getActiveBoard();
        Ui.updateBoard(Board);
    }

    private void playTurn(){
        IdentificationResponse response;
        boolean loopContinue;
        Identification currentIdentification = null;
        GroupTeam playingTeam = ((DtoGroupTeam)Engine.getActiveTeam()).getPlayingTeam();
        DtoBoard playingBoard = (DtoBoard)Engine.getActiveBoard();

        Ui.showTeam(playingTeam);
        Ui.showBoard(playingBoard, true);

        do {
            response = new IdentificationResponse();
            Ui.getResponse(response);
            try{
                currentIdentification = Engine.playTurnIdentification(response);
                loopContinue = false;
            }
            catch(IdentificationException e){
                Ui.exceptionHandler(e, false);
                loopContinue = true;
            }
        }while(loopContinue);

        playGuesserTurn(currentIdentification);
    }

    private void playGuesserTurn(Identification i_Identification){
        boolean loopContinue = false;
        GuesserResponse response;
        GroupTeam playingTeam = ((DtoGroupTeam)Engine.getActiveTeam()).getPlayingTeam();
        Dto engineResult;
        DtoGuessResult guessResult;
        boolean gameEnded = false;
        int guessCount = i_Identification.getRelated();

        Ui.showTeam(playingTeam);
        Ui.showBoard((DtoBoard)Engine.getActiveBoard(), false);
        do{
            response = new GuesserResponse();
            Ui.showIdentification(i_Identification);
            Ui.getResponse(response);

            if(response.getCardId() == EndGuessId){
                loopContinue = false;
                Ui.showTeam(((DtoGroupTeam) Engine.getActiveTeam()).getPlayingTeam());
            }
            else {
                try {
                    engineResult = Engine.playTurnGuessers(i_Identification, response);
                    if (engineResult instanceof DtoGameEndResult) {
                        guessResult = ((DtoGameEndResult) engineResult).getGuessResult();
                        gameEnded = true;
                    } else {
                        guessResult = (DtoGuessResult) engineResult;
                        engineResult = null;
                    }
                    loopContinue = guessResultHandler(guessResult, (DtoGameEndResult) engineResult,
                            guessCount, playingTeam);
                    guessCount--;
                    if (loopContinue && guessCount > 0) {
                        Ui.showBoard((DtoBoard) Engine.getActiveBoard(), false);
                    }
                } catch (CardFlippedException e) {
                    Ui.exceptionHandler(e, true);
                    loopContinue = true;
                }
            }
        }while(loopContinue && !gameEnded && (guessCount > 0));

        Engine.nextTeam();
    }

    private boolean guessResultHandler(DtoGuessResult i_GuessResult, DtoGameEndResult i_EndResult,
                                       int i_GuessLeft, GroupTeam i_CurrentTeam){
        boolean gameEnded = i_EndResult != null;
        boolean loopContinue = false;

        Ui.guessResult(i_GuessResult, i_GuessLeft - 1, i_CurrentTeam);
        if (gameEnded) {
                    Ui.victoryHandler((i_EndResult).getWinningTeam());
        }
        else if(i_GuessResult == DtoGuessResult.SUCCESSFUL_GUESS){
                loopContinue = true;
                Ui.showTeam(((DtoGroupTeam) Engine.getActiveTeam()).getPlayingTeam());
        }

        return loopContinue;
    }

    private void gameStatus(){
        Ui.showActiveGameStatus(Engine.getActiveGameStatus());
    }
}
