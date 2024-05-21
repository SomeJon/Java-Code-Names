package engine;

import dto.Dto;
import dto.type.data.DtoActiveGameStatus;
import dto.type.data.DtoGroupTeam;
import engine.board.card.Card;
import engine.data.Identification;
import engine.response.GuesserResponse;
import engine.response.IdentificationResponse;
import engine.response.Response;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface EngineInterface {
        public void loadXml(Response i_LoadXml) throws JAXBException, IOException;
        public Dto getStatus();
        public void startGame();
        public Dto getActiveBoard();
        public Dto getActiveTeam();
        public Identification playTurnIdentification(IdentificationResponse i_Response);
        public Dto playTurnGuessers(Identification i_CurrentIdentification, GuesserResponse i_Response);
        public Dto checkCardReturnContinue(Card i_GuessedCard);
        public DtoGroupTeam nextTeam();
        public DtoActiveGameStatus getActiveGameStatus();
}
