package engine;

import engine.board.Board;
import engine.board.card.Card;
import engine.board.card.CardGroup;
import engine.board.card.GroupNeutral;
import engine.board.card.GroupTeam;
import engine.data.GameData;
import engine.data.Identification;
import engine.exception.CodeNameExceptions;
import engine.exception.turn.CardFlippedException;
import engine.exception.turn.GuessOutOfRangeException;
import engine.exception.turn.IdentificationException;
import engine.response.GuesserResponse;
import engine.response.IdentificationResponse;
import engine.response.LoadXmlResponse;
import ui.veiw.UiAction;
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
    public enum GuessResult{
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
                    playTurnIdentification();
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

    private void playTurnIdentification(){
        boolean loopContinue;
        IdentificationResponse response;
        GroupTeam playingTeam = Data.getActiveData().getPlayingTeamGroup();
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
        

    }

    private void playTurnGuessers(Identification currentIdentification){
        boolean loopContinue;
        GuesserResponse response;
        GroupTeam playingTeam = Data.getActiveData().getPlayingTeamGroup();
        Board playingBoard = Data.getActiveData().getPlayingBoard();
        int maxId = Data.getStatus().getNumOfCards() + Data.getStatus().getNumOfBlackCards();

        Ui.showTeam(playingTeam);
        Ui.showBoard(playingBoard, false);
        do{
            response = new GuesserResponse();
            Ui.showIdentification(currentIdentification);
            Ui.getResponse(response);
            int cardId = response.getCardId();
            if(cardId < 0 || cardId > maxId) {
                Ui.exceptionHandler(new GuessOutOfRangeException("Card Id", cardId, maxId, 0));
                loopContinue = true;
            }
            else{
                Card guessedCard = playingBoard.getCard(cardId);
                if(guessedCard.isFlipped()) {
                    Ui.exceptionHandler(new CardFlippedException());
                    loopContinue = true;
                }
                else{
                    guessedCard.flip();
                    loopContinue = checkCardReturnContinue(guessedCard);
                }
            }
        }while(loopContinue);

        Data.getActiveData().nextTeam();
    }

    private boolean checkCardReturnContinue(Card i_GuessedCard) {
        boolean loopContinue;
        GroupTeam playingTeam = Data.getActiveData().getPlayingTeamGroup();
        Board playingBoard = Data.getActiveData().getPlayingBoard();
        CardGroup cardGroup = i_GuessedCard.getGroup();

        if(cardGroup instanceof GroupNeutral) {
            GroupNeutral NeutralGroup = (GroupNeutral) cardGroup;
            if (NeutralGroup.isBlack()) {
                Data.getActiveData().getPlayingTeams().remove(playingTeam.getTeam());
                if (Data.getActiveData().getPlayingTeams().size() == 1) {
                    //todo end game, left team wins!
                } else {
                    //todo return guess result black
                }
            } else {
                //todo return guess result neutral
            }
            loopContinue = false;
        }
        else {
            GroupTeam groupTeam = (GroupTeam) cardGroup;
            if (groupTeam != playingTeam) {
                //todo return wrong team result
                if (groupTeam.getCardsFlipped() == groupTeam.getCards()) {
                    //todo return victory
                }
                loopContinue = false;
            } else {
                //todo return correct team
                if (groupTeam.getCardsFlipped() == groupTeam.getCards()) {
                    //todo return victory
                    loopContinue = false;
                } else {
                    loopContinue = true;
                    Ui.showTeam(playingTeam);
                    Ui.showBoard(playingBoard, false);
                }
            }
        }

        return loopContinue;
    }
}
