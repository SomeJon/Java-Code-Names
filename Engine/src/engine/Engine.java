package engine;

import dto.Dto;
import dto.type.board.DtoBoard;
import dto.type.data.*;
import engine.board.Board;
import engine.board.card.Card;
import engine.board.card.GroupCard;
import engine.board.card.GroupNeutral;
import engine.board.card.GroupTeam;
import engine.data.GameData;
import engine.data.Identification;
import engine.exception.turn.CardFlippedException;
import engine.exception.turn.GuessOutOfRangeException;
import engine.exception.turn.IdentificationException;
import engine.response.GuesserResponse;
import engine.response.IdentificationResponse;
import engine.response.LoadXmlResponse;
import engine.response.Response;
import jaxb.schema.FileReader;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Engine implements EngineInterface, Serializable {

    private final GameData Data;

    public Engine() {
        Data = new GameData();
    }

    @Override
    public void loadXml(Response i_LoadXml) throws JAXBException, IOException {
        LoadXmlResponse loadXml = (LoadXmlResponse) i_LoadXml;

        File responseFile = loadXml.getInputFile();
        FileReader.ReadXml(responseFile, Data);
    }

    @Override
    public Dto getStatus(){
        return new DtoGameDetails(Data.getStatus());
    }

    @Override
    public void startGame() {
        Data.startBoard();
    }

    @Override
    public Dto getActiveBoard() {
        return new DtoBoard(Data.getActiveData().getPlayingBoard());
    }

    @Override
    public Dto getActiveTeam() {
        return new DtoGroupTeam(Data.getActiveData().getPlayingTeamGroup());
    }

    @Override
    public Identification playTurnIdentification(IdentificationResponse i_Response) {
        GroupTeam playingTeam = Data.getActiveData().getPlayingTeamGroup();

        if (i_Response.getRelated() >
                playingTeam.getCards() - playingTeam.getCardsFlipped() || i_Response.getRelated() < 1) {
            throw new IdentificationException("Related words", i_Response.getRelated(),
                    playingTeam.getCards() - playingTeam.getCardsFlipped(), 1);
        }

        return new Identification(i_Response.getIdentification(), i_Response.getRelated());
    }

    @Override
    public Dto playTurnGuessers(Identification i_CurrentIdentification, GuesserResponse i_Response){
        GroupTeam playingTeam = Data.getActiveData().getPlayingTeamGroup();
        Board playingBoard = Data.getActiveData().getPlayingBoard();
        int maxId = Data.getStatus().getNumOfCards() + Data.getStatus().getNumOfBlackCards();
        int cardId = i_Response.getCardId();
        Dto ret;

        if(cardId < 0 || cardId > maxId) {
                throw new GuessOutOfRangeException("Card Id", cardId, maxId, 1);
        } else {
            Card guessedCard = playingBoard.getCard(cardId);
            if (guessedCard.isFlipped()) {
                throw new CardFlippedException();
            } else {
                guessedCard.flip();
                ret = checkCardReturnContinue(guessedCard);
            }
        }

        return ret;
    }

    @Override
    public Dto checkCardReturnContinue(Card i_GuessedCard) {
        GroupTeam playingTeam = Data.getActiveData().getPlayingTeamGroup();
        GroupCard cardGroup = i_GuessedCard.getGroup();
        Dto returnedValue;

        if(cardGroup instanceof GroupNeutral) {
            GroupNeutral NeutralGroup = (GroupNeutral) cardGroup;
            if (NeutralGroup.isBlack()) {
                DtoGuessResult.BLACK_HIT.setGroupTeam(new DtoGroupTeam(playingTeam));
                Data.getActiveData().endCurrentTeam();
                if (Data.getActiveData().getPlayingTeams().size() == 1) {
                    returnedValue = new DtoGameEndResult(Data.getActiveData().getPlayingTeamGroup(), DtoGuessResult.BLACK_HIT);
                } else {
                    returnedValue = DtoGuessResult.BLACK_HIT;
                }
            } else {
                returnedValue = DtoGuessResult.NEUTRAL_HIT;
            }
        }
        else {
            GroupTeam groupTeam = (GroupTeam) cardGroup;
            if (groupTeam != playingTeam) {
                DtoGuessResult.ENEMY_TEAM_HIT.setGroupTeam(new DtoGroupTeam(groupTeam));
                if(groupTeam.getCardsFlipped() == groupTeam.getCards()) {
                    returnedValue = new DtoGameEndResult(groupTeam, DtoGuessResult.ENEMY_TEAM_HIT);
                }
                else{
                    returnedValue = DtoGuessResult.ENEMY_TEAM_HIT;
                }
            } else {
                if (groupTeam.getCardsFlipped() == groupTeam.getCards()) {
                    returnedValue = new DtoGameEndResult(groupTeam, DtoGuessResult.SUCCESSFUL_GUESS);
                } else {
                    returnedValue = DtoGuessResult.SUCCESSFUL_GUESS;
                }
            }
        }

        return returnedValue;
    }

    @Override
    public DtoGroupTeam nextTeam() {
        GroupTeam nextTeam = Data.getActiveData().nextTeam();
        return new DtoGroupTeam(nextTeam);
    }

    @Override
    public DtoActiveGameStatus getActiveGameStatus() {
        return new DtoActiveGameStatus(Data.getActiveData().getPlayingBoard(),
                Data.getActiveData().getPlayingTeamGroup());
    }
}
