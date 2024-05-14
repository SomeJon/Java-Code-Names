package engine.data;

import java.util.List;

public class GameStatus {
    private List<Team> Teams;
    private Integer NumOfWords;
    private Integer NumOfBlackWords;
    private Integer NumOfCards;
    private Integer NumOfBlackCards;

    public GameStatus(List<Team> teams, Integer numOfWords, Integer numOfBlackWords, Integer numOfCards, Integer numOfBlackCards) {
        Teams = teams;
        NumOfWords = numOfWords;
        NumOfCards = numOfCards;
        NumOfBlackWords = numOfBlackWords;
        NumOfBlackCards = numOfBlackCards;
    }
}
