package engine.data;

import engine.board.Board;

public class ActiveGame {
    private final Board PlayingBoard;
    private final Team PlayingTeam;

    public Board getPlayingBoard() {
        return PlayingBoard;
    }

    public Team getPlayingTeam() {
        return PlayingTeam;
    }

    public ActiveGame(engine.board.Board Board, Team Team) {
        PlayingBoard = Board;
        PlayingTeam = Team;
    }
}
