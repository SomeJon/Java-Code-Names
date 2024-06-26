package engine.exception;

public abstract class CodeNameException extends RuntimeException {
    public enum ExceptionType {
        LOAD_FILE,
        CHECK_PATH,
        TURN_EXCEPTION,
        CARD_FLIPPED
    }

    private ExceptionType Type;

    public ExceptionType getType() {
        return Type;
    }

    public void setType(ExceptionType i_Type) {
        Type = i_Type;
    }
}
