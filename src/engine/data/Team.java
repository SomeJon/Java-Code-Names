package engine.data;

import engine.board.card.CardGroup;

public class Team extends CardGroup{
    private final String Name;
    private final int PointGoal;


    public String getName() {
        return Name;
    }

    public int getPointGoal() {
        return PointGoal;
    }

    public Team(String name, int cardsOnBoard) {
        Name = name;
        PointGoal = cardsOnBoard;
    }
}
