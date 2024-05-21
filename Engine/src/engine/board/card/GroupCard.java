package engine.board.card;


import java.io.Serializable;

public abstract class GroupCard implements Serializable {
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
