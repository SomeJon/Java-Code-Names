package engine.board.card;

import engine.data.Team;

import java.util.Objects;

public class GroupTeam extends GroupCard {
    private final Team Team;

    public GroupTeam(Team i_Team, int i_NumOfCards) {
        Cards = i_NumOfCards;
        Team = i_Team;
    }

    public GroupTeam(Team i_Team, int i_NumOfCards, int i_CardsFlipped) {
        Cards = i_NumOfCards;
        Team = i_Team;
        CardsFlipped = i_CardsFlipped;
    }

    public String getName() {
        return Team.getName();
    }

    public Team getTeam() {
        return Team;
    }

    @Override
    public GroupCard getCopy() {
        return new GroupTeam(new Team(Team.getName(), Team.getPointGoal()), Cards, CardsFlipped);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupTeam groupTeam = (GroupTeam) o;
        return Objects.equals(Team, groupTeam.Team);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Team);
    }
}
