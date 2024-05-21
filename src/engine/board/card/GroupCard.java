package engine.board.card;


public abstract class GroupCard{
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

    public abstract GroupCard getCopy();
}
