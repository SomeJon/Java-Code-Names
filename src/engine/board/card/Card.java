package engine.board.card;


public class Card {
    private boolean Flipped = false;
    private final String Text;
    private Integer ID;
    private final CardGroup Group;

    public String getText() {
        return Text;
    }

    public CardGroup getGroup() {
        return Group;
    }

    public boolean isFlipped() {
        return Flipped;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Card(String text, CardGroup group) {
        Text = text;
        Group = group;
    }

    public void flip(){
        Flipped = !Flipped;
    }
}
