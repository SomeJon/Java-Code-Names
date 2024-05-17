package engine.board.card;


public abstract class CardGroup {
    protected int CardsLeft;
    protected int CardsFlipped = 0;

    public int getCardsLeft() {
        return CardsLeft;
    }

    public int getCardsFlipped() {
        return CardsFlipped;
    }

    public void cardDown(){
        CardsLeft--;
        CardsFlipped++;
    }
}
