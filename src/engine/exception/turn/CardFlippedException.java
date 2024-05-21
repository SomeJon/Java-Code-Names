package engine.exception.turn;

import engine.exception.CodeNameExceptions;

public class CardFlippedException extends CodeNameExceptions {
    public CardFlippedException() {
        super.setType(ExceptionType.CARD_FLIPPED);
    }
}
