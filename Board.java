import java.util.ArrayList;

public class Board {

    public static final int BLACK = 1;
    public static final int WHITE = -1;
    private static final int EMPTY = 0;

    private static final int ROWS = 8;
    private static final int COLS = ROWS;

    private int[][] gameBoard;
    private int lastPlayer;

    private ArrayList<Board> children;
    private Move lastMove;
    private int bestExpectedValue;

    Board() {

        gameBoard = new int[ROWS][COLS];
        lastPlayer = EMPTY;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gameBoard[i][j] = EMPTY;
            }
        }
//        gameBoard[3][3] = WHITE;
//        gameBoard[3][4] = BLACK;
//        gameBoard[4][3] = WHITE;
//        gameBoard[4][4] = WHITE;
//        gameBoard[4][5] = WHITE;
//        gameBoard[5][6] = WHITE;

        gameBoard[3][3] = WHITE;
        gameBoard[3][4] = BLACK;
        gameBoard[4][3] = BLACK;
        gameBoard[4][4] = WHITE;

//        gameBoard[2][1] = BLACK;
//        gameBoard[2][2] = BLACK;
//        gameBoard[2][3] = BLACK;
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




    public void produceChildren() {
        if(children==null) {
            children = new ArrayList<>();

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if(gameBoard[i][j]==EMPTY) {
                        Board child = ifValidMakeMove(i, j, -1 * lastPlayer); //the next player
                        if (child != null) {
                            child.setLastPlayer(-1 * lastPlayer);
                            children.add(child);
                        }
                    }
                }
            }
        }else
            return;
    }

    public boolean isTerminal() {
        produceChildren();

        if (!children.isEmpty()) {
            return false;
        }

        return true;
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
                    break;
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
                    break;
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
                    break;
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
                    break;
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
                    break;
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
                    break;
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
                    break;
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
                    break;
                }
            }
            temp.gameBoard[row+i][col-i] = COLOR;
        }

        if (!childBirth) return null;

        child.gameBoard[row][col] = COLOR;  //played square changes color
        return child;
    }

    private void makeMove(int row, int col){

    }




    public int evaluate(){
        int cornerEval = gameBoard[0][0] + gameBoard[0][7] + gameBoard[7][0] + gameBoard[7][7];
        int sideEval = 0;
        int allEval = 0;

        for(int i=0; i<ROWS; i++){
            for (int j=0; j<COLS; j++){
                allEval += gameBoard[i][j];

                if((i==0 || i==7) && (j!=0 && j!=7)){       //sides: row0 and row7
                    sideEval += gameBoard[i][j];
                }
                else if((j==0 || j==7) && (i!=0 && i!=7)){   //sides: col0 and col7
                    sideEval += gameBoard[i][j];
                }
            }
        }

        return 3*cornerEval + 2*sideEval + allEval;
    }

    public void print() {
        System.out.println("  1 2 3 4 5 6 7 8");
        for (int i = 0; i < ROWS; i++) {
            System.out.print((i+1)+" ");
            for (int j = 0; j < COLS; j++) {
                char c = gameBoard[i][j]!=0 ? (gameBoard[i][j]==1 ? 'B': 'W') : 'o';
                System.out.print(c+" ");
            }
            System.out.println();
        }
    }




    public void setLastPlayer(int lastPlayer){
        this.lastPlayer = lastPlayer;
    }

    public int getLastPlayer() {
        return lastPlayer;
    }

    public void setBestExpectedValue(int value) {
        this.bestExpectedValue = value;
    }

    public int getBestExpectedValue() {
        return bestExpectedValue;
    }

    public ArrayList<Board> getChildren() {return children;}

}