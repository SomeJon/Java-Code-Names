package engine.exception;

public abstract class CodeNameExceptions extends RuntimeException {
    public enum ExceptionType {
        LOAD_FILE,
        CHECK_PATH
    }

    private ExceptionType Type;

    public ExceptionType getType() {
        return Type;
    }

    public void setType(ExceptionType i_Type) {
        Type = i_Type;
    }
}
