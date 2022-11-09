import java.util.ArrayList;
import java.util.Set;

public class Board {

    public static final int BLACK = 1;
    public static final int WHITE = -1;
    private static final int EMPTY = 0;

    private static final int ROWS = 8;
    private static final int COLS = ROWS;
    private static int winner = EMPTY;

    private final int[][] gameBoard;
    private int lastPlayer;

    private ArrayList<Board> children;
    public Move lastMove;
    private int bestExpectedValue;

    Board() {

        gameBoard = new int[ROWS][COLS];
        lastMove = new Move();
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
        lastMove = new Move(fatherBoard.lastMove.getRow(), fatherBoard.lastMove.getCol());
        gameBoard = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(fatherBoard.gameBoard[i], 0, gameBoard[i], 0, COLS);
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
                            child.lastMove.makeMove(i, j);
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

        return children.isEmpty();
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

    public void reset() {
        if (children != null) {
            children.clear();
            children = null;
        }

        setLastPlayer(-1*lastPlayer);
    }

    /*public void print() {
        System.out.println("\t1\t2\t3\t4\t5\t6\t7\t8");
        for (int i = 0; i < ROWS; i++) {
            System.out.print((i+1)+" ");
            for (int j = 0; j < COLS; j++) {
                String c = gameBoard[i][j]!=0 ? (gameBoard[i][j]==1 ? "\u26ab": "\u26aa") : " \u26d2";
                System.out.print(c+"  ");
            }
            System.out.println();
        }
    }*/

    public String boardToString(Set<Move> availMoves){
        StringBuilder str = new StringBuilder("\t\t+    1   2   3   4    5   6   7    8\n");

        String c;

        for (int i = 0; i < ROWS; i++) {
            str.append("\t\t").append(i + 1).append(" ");

            for (int j = 0; j < COLS; j++) {
                if(gameBoard[i][j]!=0)
                    c = gameBoard[i][j] == 1 ? "\u26ab" : "\u26aa";
                else{
                    if (availMoves != null && availMoves.contains(new Move(i,j)))
                        c = "X";
                    else
                        c = "\u2b55";
                }
                str.append(c).append("  ");
            }
            str.append("\n");
        }
        return new String(str);
    }

    public int calcScore(){
        int scoreBlack = 0;
        int scoreWhite = 0;
        for(int i=0; i<ROWS; i++)
            for(int j=0; j<COLS; j++) {
                scoreBlack += (gameBoard[i][j] ==  1) ? 1 : 0;
                scoreWhite += (gameBoard[i][j] == -1) ? 1 : 0;
            }
        if(scoreBlack > scoreWhite) {
            winner = BLACK;
            return scoreWhite;

        }else if(scoreBlack < scoreWhite) {
            winner = WHITE;
            return scoreBlack;

        }else   //tie
            return 32;
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

    public int getWinner(){
        return winner;
    }

}