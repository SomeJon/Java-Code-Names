package engine.exception.loadxml;

import engine.exception.CodeNameExceptions;

import java.util.List;

public class TeamNamesNotUnique extends CodeNameExceptions {
    List<String> NonUniqueNames;

    public TeamNamesNotUnique(List<String> i_NunUniqueNames) {
        NonUniqueNames = i_NunUniqueNames;
    }

    public List<String> getNonUniqueNames() {
        return NonUniqueNames;
    }
}
