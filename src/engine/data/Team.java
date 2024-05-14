package engine.data;

import engine.board.card.CardGroup;

public class Team extends CardGroup{
    private final String Name;
    private final int CardsOnBoard;
    private int CardsFound;

    public Team(String name, int cardsOnBoard) {
        Name = name;
        CardsOnBoard = cardsOnBoard;
        CardsFound = 0;
    }

    public void foundCard(){
        CardsFound++;
    }
}
