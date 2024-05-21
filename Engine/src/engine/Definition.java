package engine;

import engine.data.Team;


public class Definition{
    private final String ChosenDefinition;
    private final Integer NumOfWords;
    private final Team DefinerGroup;


    public Definition(String chosenDefinition, Integer numOfWords, Team definerGroup) {
        ChosenDefinition = chosenDefinition;
        NumOfWords = numOfWords;
        DefinerGroup = definerGroup;
    }


    public String getChosenDefinition() {
        return ChosenDefinition;
    }

    public Integer getNumOfWords() {
        return NumOfWords;
    }

    public Team getDefinerGroup() {
        return DefinerGroup;
    }
}
