package engine.board.card;


import java.io.Serializable;

public class Card implements Serializable {
    private boolean Flipped = false;
    private final String Text;
    private Integer ID;
    private final GroupCard Group;

    public String getText() {
        return Text;
    }

    public GroupCard getGroup() {
        return Group;
    }

    public boolean isFlipped() {
        return Flipped;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer i_ID) {
        this.ID = i_ID;
    }

    public Card(String i_Text, GroupCard i_Group) {
        Text = i_Text;
        Group = i_Group;
    }

    public Card(String i_Text, GroupCard i_Group, int i_Id, boolean i_isFlipped) {
        Text = i_Text;
        Group = i_Group;
        setID(i_Id);
        Flipped = i_isFlipped;
    }

    public void flip(){
        Flipped = !Flipped;
        Group.cardDown();
    }
}
