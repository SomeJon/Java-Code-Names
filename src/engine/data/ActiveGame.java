package engine.data;

import engine.board.Board;
import engine.board.card.GroupTeam;

public class ActiveGame {
    private final Board PlayingBoard;
    private final GroupTeam PlayingTeamGreoup;

    public Board getPlayingBoard() {
        return PlayingBoard;
    }

    public GroupTeam getPlayingTeamGreoup() {
        return PlayingTeamGreoup;
    }

    public ActiveGame(engine.board.Board Board, GroupTeam Team) {
        PlayingBoard = Board;
        PlayingTeamGreoup = Team;
    }
}
