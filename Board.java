import java.util.ArrayList;

public class Board {

    private static final int BLACK = 1;
    private static final int WHITE = -1;
    private static final int EMPTY = 0;

    private static final int ROWS = 8;
    private static final int COLS = ROWS;

    private int[][] gameBoard;
    private int lastPlayer;
    private int nextPlayer;
    private ArrayList<Board> children;

    Board() {

        gameBoard = new int[ROWS][COLS];
        lastPlayer = EMPTY;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gameBoard[i][j] = EMPTY;
            }
        }
        gameBoard[3][3] = WHITE;
        gameBoard[3][4] = BLACK;
        gameBoard[4][3] = BLACK;
        gameBoard[4][4] = WHITE;
    }

    Board(Board fatherBoard) {

        lastPlayer = fatherBoard.lastPlayer;
        gameBoard = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gameBoard[i][j] = fatherBoard.gameBoard[i][j];
            }
        }
    }

    private void produceChildren() {
        children = new ArrayList<>();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Board child = ifValidMakeMove(i,j, nextPlayer);
                if (child != null) {
                    children.add(child);
                }
            }
        }
    }

    public boolean isTerminal(boolean byProducingChildren) {
        // current player
        if (byProducingChildren) {
            produceChildren();

            if (!children.isEmpty()) {
                return false;
            }
        }

        //lastPlayer again
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (isValidMove(i,j, lastPlayer))
                    return false;
            }
        }

        return true;
    }

    public boolean isValidMove(int row, int col, int COLOR) {

        //kathetos-panw
        for (int i = row - 1; i >= 0; i--) {
            if (gameBoard[i][col] == EMPTY) {
                break;
            }
            if (gameBoard[i][col] == COLOR) {
                if (i == row - 1) {
                    break;
                } else {
                    return true;
                }
            }
        }

        //kathetos-katw
        for (int i = row + 1; i < ROWS; i++) {
            if (gameBoard[i][col] == EMPTY) {
                break;
            }
            if (gameBoard[i][col] == COLOR) {
                if (i == row + 1) {
                    break;
                } else {
                    return true;
                }
            }
        }
        //orizontia-aristera
        for (int j = col - 1; j >= 0; j--) {
            if (gameBoard[row][j] == EMPTY) {
                break;
            }
            if (gameBoard[row][j] == COLOR) {
                if (j == col - 1) {
                    break;
                } else {
                    return true;
                }
            }
        }

        //orizontia-deksia
        for (int j = col + 1; j < COLS; j++) {
            if (gameBoard[row][j] == EMPTY) {
                break;
            }
            if (gameBoard[row][j] == COLOR) {
                if (j == col + 1) {
                    break;
                } else {
                    return true;
                }
            }
        }

        //panw-aristera
        for (int i = 1; i <= Math.min(row, col); i++) {
            if (gameBoard[row-i][col-i] == EMPTY) {
                break;
            }
            if (gameBoard[row-i][col-i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    return true;
                }
            }
        }

        //katw-deksia
        for (int i = 1; i <= Math.min(ROWS-1-row, COLS-1-col); i++) {
            if (gameBoard[row+i][col+i] == EMPTY) {
                break;
            }
            if (gameBoard[row+i][col+i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    return true;
                }
            }
        }

        //panw-deksia
        for (int i = 1; i <= Math.min(row, COLS-1-col); i++) {
            if (gameBoard[row-i][col+i] == EMPTY) {
                break;
            }
            if (gameBoard[row-i][col+i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    return true;
                }
            }
        }

        //katw-aristera
        for (int i = 1; i <= Math.min(ROWS-1-row, col); i++) {
            if (gameBoard[row+i][col-i] == EMPTY) {
                break;
            }
            if (gameBoard[row+i][col-i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    return true;
                }
            }
        }

        return false;
    }


    private Board ifValidMakeMove(int row, int col, int COLOR) {

        Board child = new Board(this);
        boolean childBirth = false;

        Board temp = new Board(child);
        //kathetos-panw
        for (int i = row - 1; i >= 0; i--) {
            if (gameBoard[i][col] == EMPTY) {
                break;
            }
            if (gameBoard[i][col] == COLOR) {
                if (i == row - 1) {
                    break;
                } else {
                    child = temp;
                    childBirth = true;
                }
            }
            temp.gameBoard[i][col] = COLOR;
        }

        temp = new Board(child);
        //kathetos-katw
        for (int i = row + 1; i < ROWS; i++) {
            if (gameBoard[i][col] == EMPTY) {
                break;
            }
            if (gameBoard[i][col] == COLOR) {
                if (i == row + 1) {
                    break;
                } else {
                    child = temp;
                    childBirth = true;
                }
            }
            temp.gameBoard[i][col] = COLOR;
        }

        temp = new Board(child);
        //orizontia-aristera
        for (int j = col - 1; j >= 0; j--) {
            if (gameBoard[row][j] == EMPTY) {
                break;
            }
            if (gameBoard[row][j] == COLOR) {
                if (j == col - 1) {
                    break;
                } else {
                    child = temp;
                    childBirth = true;
                }
            }
            temp.gameBoard[row][j] = COLOR;
        }

        temp = new Board(child);
        //orizontia-deksia
        for (int j = col + 1; j < COLS; j++) {
            if (gameBoard[row][j] == EMPTY) {
                break;
            }
            if (gameBoard[row][j] == COLOR) {
                if (j == col + 1) {
                    break;
                } else {
                    child = temp;
                    childBirth = true;
                }
            }
            temp.gameBoard[row][j] = COLOR;
        }

        temp = new Board(child);
        //panw-aristera
        for (int i = 1; i <= Math.min(row, col); i++) {
            if (gameBoard[row-i][col-i] == EMPTY) {
                break;
            }
            if (gameBoard[row-i][col-i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    child = temp;
                    childBirth = true;
                }
            }
            temp.gameBoard[row-i][col-i] = COLOR;
        }

        temp = new Board(child);
        //katw-deksia
        for (int i = 1; i <= Math.min(ROWS-1-row, COLS-1-col); i++) {
            if (gameBoard[row+i][col+i] == EMPTY) {
                break;
            }
            if (gameBoard[row+i][col+i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    child = temp;
                    childBirth = true;
                }
            }
            temp.gameBoard[row+i][col+i] = COLOR;
        }

        temp = new Board(child);
        //panw-deksia
        for (int i = 1; i <= Math.min(row, COLS-1-col); i++) {
            if (gameBoard[row-i][col+i] == EMPTY) {
                break;
            }
            if (gameBoard[row-i][col+i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    child = temp;
                    childBirth = true;
                }
            }
            temp.gameBoard[row-i][col+i] = COLOR;
        }

        temp = new Board(child);
        //katw-aristera
        for (int i = 1; i <= Math.min(ROWS-1-row, col); i++) {
            if (gameBoard[row+i][col-i] == EMPTY) {
                break;
            }
            if (gameBoard[row+i][col-i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    child = temp;
                    childBirth = true;
                }
            }
            temp.gameBoard[row+i][col-i] = COLOR;
        }

        if (!childBirth) return null;
        return child;
    }


    private void makeMove(int row, int col){

    }

    public void evaluate(){}

    public void print(){}

    ArrayList<Board> getChildren() {return children;}
}
