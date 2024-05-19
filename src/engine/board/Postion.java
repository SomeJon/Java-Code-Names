package engine.board;

public class Postion {
    private final int row;
    private final int col;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private Postion(int i_row, int i_col) {
        row = i_row;
        col = i_col;
    }

    public static Postion getPostion(int i_Numerical, int i_NumOfColumns) {
        int id = i_Numerical - 1;
        int row = id / i_NumOfColumns;
        int col = id % i_NumOfColumns;

        return new Postion(row, col);
    }
}
