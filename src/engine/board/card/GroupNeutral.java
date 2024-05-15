package engine.board.card;

public class GroupNeutral extends CardGroup{
    private final boolean IsBlack;


    public boolean isBlack() {
        return IsBlack;
    }

    public GroupNeutral(boolean i_IsBlack, Integer i_NumOfCards, String i_Name) {
        NumOfCards = i_NumOfCards;
        IsBlack = i_IsBlack;
        Name = i_Name;
    }
}
