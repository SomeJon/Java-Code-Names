package engine.exception.turn;

import engine.exception.CodeNameException;

public class CardFlippedException extends CodeNameException {
    public CardFlippedException() {
        super.setType(ExceptionType.CARD_FLIPPED);
    }
}
