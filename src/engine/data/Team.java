package engine.data;

import engine.board.card.CardGroup;

public class Team extends CardGroup{
    private final String Name;
    private final int BoardCards;
    private int CardsFound;

    public String getName() {
        return Name;
    }

    public int getBoardCards() {
        return BoardCards;
    }

    public int getCardsFound() {
        return CardsFound;
    }

    public Team(String name, int cardsOnBoard) {
        Name = name;
        BoardCards = cardsOnBoard;
        CardsFound = 0;
    }

    public void foundCard(){
        CardsFound++;
    }
}
