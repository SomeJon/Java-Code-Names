package dto.type.data;

import dto.Dto;
import dto.type.board.DtoBoard;
import engine.board.Board;
import engine.board.card.GroupTeam;

public class DtoActiveGameStatus implements Dto {
    private final DtoBoard Board;
    private final GroupTeam NextPlayingTeam;


    public DtoBoard getBoard() {
        return Board;
    }

    public GroupTeam getNextPlayingTeam() {
        return NextPlayingTeam;
    }

    public DtoActiveGameStatus(Board i_Board, GroupTeam nextPlayingTeam) {
        Board = new DtoBoard(i_Board);
        NextPlayingTeam = (GroupTeam) nextPlayingTeam.getCopy();
    }
}
