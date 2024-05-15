package engine.data;

import java.util.List;

public class GameStatus {
    private final List<Team> Teams;
    private final Integer NumOfWords;
    private final Integer NumOfBlackWords;
    private final Integer NumOfCards;
    private final Integer NumOfBlackCards;

    public List<Team> getTeams() {
        return Teams;
    }

    public Integer getNumOfWords() {
        return NumOfWords;
    }

    public Integer getNumOfBlackWords() {
        return NumOfBlackWords;
    }

    public Integer getNumOfCards() {
        return NumOfCards;
    }

    public Integer getNumOfBlackCards() {
        return NumOfBlackCards;
    }

    public GameStatus(List<Team> teams, Integer numOfWords, Integer numOfBlackWords,
                      Integer numOfCards, Integer numOfBlackCards) {
        int sumOfTeamCards = teams.stream()
                .mapToInt(Team::getBoardCards)
                .sum();
        boolean uniqueNames = teams.stream()
                .map(Team::getName)
                .distinct()
                .count() == teams.size();

        if (!uniqueNames) {
            throw new IllegalArgumentException("Teams must have unique names");
        }
        if(numOfWords < numOfCards){
            throw new IllegalArgumentException("Number of words must be greater than the number of words");
        }
        if(numOfBlackWords < numOfBlackCards){
            throw new IllegalArgumentException("Number of black words must be greater than the number of black words");
        }
        if(sumOfTeamCards > numOfCards){
            throw  new IllegalArgumentException("sumOfTeamCards must be less than numOfCards");
        }

        Teams = teams;
        NumOfWords = numOfWords;
        NumOfCards = numOfCards;
        NumOfBlackWords = numOfBlackWords;
        NumOfBlackCards = numOfBlackCards;
    }
}
