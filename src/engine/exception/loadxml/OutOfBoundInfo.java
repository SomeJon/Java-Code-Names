package engine.exception.loadxml;

import engine.exception.OutOfBoundException;

public class OutOfBoundInfo extends OutOfBoundException {
    public enum FileErrorType{
        CARDS_COUNT,
        BLACK_CARDS_COUNT,
        TEAMS_CARDS_COUNT,
        MATRIX_CARDS_COUNT
    }

    private final FileErrorType fileErrorType;

    public OutOfBoundInfo(FileErrorType fileErrorType, String parameterName, int parameterValue, int max, int min) {
        super(parameterName, parameterValue, max, min);
        this.fileErrorType = fileErrorType;
    }

    public FileErrorType getFileErrorType() {
        return fileErrorType;
    }
}
