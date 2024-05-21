package engine.board.card;

import java.io.Serializable;
import java.util.Objects;

public class GroupNeutral extends GroupCard implements Serializable{
    private final boolean IsBlack;


    public boolean isBlack() {
        return IsBlack;
    }

    public GroupNeutral(boolean i_IsBlack, int i_NumOfCards) {
        Cards = i_NumOfCards;
        IsBlack = i_IsBlack;
    }

    public GroupNeutral(boolean i_IsBlack, int i_NumOfCards, int i_FlippedCards) {
        Cards = i_NumOfCards;
        IsBlack = i_IsBlack;
        CardsFlipped = i_FlippedCards;
    }

    @Override
    public GroupCard getCopy() {
        return new GroupNeutral(IsBlack, Cards, CardsFlipped);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupNeutral that = (GroupNeutral) o;
        return IsBlack == that.IsBlack;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(IsBlack);
    }
}
