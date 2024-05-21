package engine.data;

import java.util.Objects;

public class Team{
    private final String Name;
    private final int PointGoal;


    public String getName() {
        return Name;
    }

    public int getPointGoal() {
        return PointGoal;
    }

    public Team(String i_Name, int i_CardsOnBoard) {
        Name = i_Name;
        PointGoal = i_CardsOnBoard;
    }

    public Team(Team i_Team) {
        this.Name = i_Team.Name;
        this.PointGoal = i_Team.PointGoal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(Name, team.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Name);
    }
}
