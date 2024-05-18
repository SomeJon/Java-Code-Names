package engine.exception.loadxml;

import engine.exception.CodeNameExceptions;

public class FileIsNotInPath extends CodeNameExceptions {
    String GivenPath;

    public FileIsNotInPath(String GivenPath) {
        this.GivenPath = GivenPath;
    }
}
