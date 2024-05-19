package engine.data;

import engine.exception.loadxml.OutOfBoundLoad;
import engine.exception.loadxml.TeamNamesNotUnique;

import java.util.List;
import java.util.stream.Collectors;

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
                      Integer numOfCards, Integer numOfBlackCards){
        int sumOfTeamCards = teams.stream()
                .mapToInt(Team::getPointGoal)
                .sum();
        boolean uniqueNames = teams.stream()
                .map(Team::getName)
                .distinct()
                .count() == teams.size();

        if (!uniqueNames) {
            List<String> teamNames = teams.stream()
                    .map(Team::getName)
                    .collect(Collectors.toList());
            throw new TeamNamesNotUnique(teamNames);
        }
        if(numOfWords < numOfCards){
            throw new OutOfBoundLoad("Cards", numOfCards, numOfWords, 0);
        }
        if(numOfBlackWords < numOfBlackCards){
            throw new OutOfBoundLoad("Black Cards", numOfBlackCards, numOfBlackWords, 0);
        }
        if(numOfCards < sumOfTeamCards){
            throw  new OutOfBoundLoad("Team Cards", sumOfTeamCards, numOfCards, 0);
        }

        Teams = teams;
        NumOfWords = numOfWords;
        NumOfCards = numOfCards;
        NumOfBlackWords = numOfBlackWords;
        NumOfBlackCards = numOfBlackCards;
    }
}
