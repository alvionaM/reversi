import java.util.ArrayList;

public class Board {

    int BLACK = 1;
    int WHITE = -1;
    int EMPTY = 0;

    private static final int ROWS = 8;
    private static final int COLS = 8;
    private int[][] gameBoard;

    Board() {

        gameBoard = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++){
                gameBoard[i][j] = EMPTY;
            }
        }
    }



    ArrayList<Board> getChildren() {return new ArrayList();}

    public boolean isTerminal() {return false;}

    public boolean isValidMove() {return false; }

    public void evaluate(){}

    public void print(){}
}
