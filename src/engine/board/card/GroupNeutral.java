package engine.board.card;

public class GroupNeutral extends CardGroup{
    private final boolean IsBlack;


    public boolean isBlack() {
        return IsBlack;
    }

    public GroupNeutral(boolean i_IsBlack, Integer i_NumOfCards) {
        Cards = i_NumOfCards;
        IsBlack = i_IsBlack;
    }
}
