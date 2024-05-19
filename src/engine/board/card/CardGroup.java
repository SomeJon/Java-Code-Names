package engine.board.card;


public abstract class CardGroup {
    protected int Cards;
    protected int CardsFlipped = 0;

    public int getCards() {
        return Cards;
    }

    public int getCardsFlipped() {
        return CardsFlipped;
    }

    public void cardDown(){
        CardsFlipped++;
    }
}
