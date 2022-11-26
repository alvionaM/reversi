import java.util.ArrayList;
import java.util.Set;

public class Board {

    public static final int BLACK = 1;
    public static final int WHITE = -1;
    public static final int EMPTY = 0;

    public static final String BLACKDISK = "\u26ab";
    public static final String WHITEDISK = "\u26aa";
    private static final String EMPTYDISK = "\u2b55";
    private static final String BALLDISK = "\u26bd";

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
        copyBoard(fatherBoard.gameBoard, gameBoard);
    }

    Board(Board fatherBoard, int[][] childGameBoard){
        lastPlayer = fatherBoard.lastPlayer;
        lastMove = new Move(fatherBoard.lastMove.getRow(), fatherBoard.lastMove.getCol());
        gameBoard = childGameBoard;
    }

    //Checks if any square around (i,j) has the specified color (opposite to (i,j))
    //If yes, (i,j) is a possible move. Otherwise, it's not possible.
    private boolean checkIfPossible(int i, int j, int COLOR) {
        
        if (i-1 >= 0) {
            if (j-1 >= 0) {
                if (gameBoard[i-1][j-1]==COLOR)
                    return true;
            }
            if(j+1 <= 7) {
                if (gameBoard[i-1][j+1]==COLOR)
                    return true;
            }
            if (gameBoard[i-1][j]==COLOR)
                return true;
        }
        if (i+1 <= 7) {
            if (j-1 >= 0) {
                if (gameBoard[i+1][j-1]==COLOR)
                    return true;
            }
            if (j+1 <= 7) {
                if (gameBoard[i+1][j+1]==COLOR)
                    return true;
            }
            if (gameBoard[i+1][j]==COLOR)
                return true;
        }
        if ((j-1 >= 0 && gameBoard[i][j-1]==COLOR)|| (j+1 <=7 && gameBoard[i][j+1]==COLOR)) return true;

        return false;
    }


    public void produceChildren() {
        if(children==null) {
            children = new ArrayList<>();

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if(gameBoard[i][j]==EMPTY && checkIfPossible(i, j, lastPlayer)) {
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

        if (children.isEmpty()) {
            Board copyBoard = new Board(this);
            copyBoard.setLastPlayer(-1 * copyBoard.lastPlayer);
            copyBoard.produceChildren();

            if (copyBoard.children.isEmpty())
                return true;
            else {
                children.add(copyBoard);
                return false;
            }
        }
        else
            return false;

        //return children.isEmpty();
    }

    private Board ifValidMakeMove(int row, int col, int COLOR) {

        boolean childBirth = false;


        int[][] temp = new int[8][8];
        int[][] childGameBoard = new int[8][8];
        copyBoard(gameBoard, childGameBoard);
        copyBoard(childGameBoard, temp);


        // vertical-top
        for (int i = row - 1; i >= 0; i--) {
            if (gameBoard[i][col] == EMPTY) {
                break;
            }
            if (gameBoard[i][col] == COLOR) {
                if (i == row - 1) {
                    break;
                } else {
                    copyBoard(temp, childGameBoard);
                    childBirth = true;
                    break;
                }
            }
            temp[i][col] = COLOR;
        }

        copyBoard(childGameBoard, temp);

        // vertical-bottom
        for (int i = row + 1; i < ROWS; i++) {
            if (gameBoard[i][col] == EMPTY) {
                break;
            }
            if (gameBoard[i][col] == COLOR) {
                if (i == row + 1) {
                    break;
                } else {
                    copyBoard(temp, childGameBoard);
                    childBirth = true;
                    break;
                }
            }
            temp[i][col] = COLOR;
        }

        copyBoard(childGameBoard, temp);
 
        // horizontal-left
        for (int j = col - 1; j >= 0; j--) {
            if (gameBoard[row][j] == EMPTY) {
                break;
            }
            if (gameBoard[row][j] == COLOR) {
                if (j == col - 1) {
                    break;
                } else {
                    copyBoard(temp, childGameBoard);
                    childBirth = true;
                    break;
                }
            }
            temp[row][j] = COLOR;
        }

        copyBoard(childGameBoard, temp);

        // horizontal-right
        for (int j = col + 1; j < COLS; j++) {
            if (gameBoard[row][j] == EMPTY) {
                break;
            }
            if (gameBoard[row][j] == COLOR) {
                if (j == col + 1) {
                    break;
                } else {
                    copyBoard(temp, childGameBoard);
                    childBirth = true;
                    break;
                }
            }
            temp[row][j] = COLOR;
        }

        copyBoard(childGameBoard, temp);
        
        // top-left
        for (int i = 1; i <= Math.min(row, col); i++) {
            if (gameBoard[row-i][col-i] == EMPTY) {
                break;
            }
            if (gameBoard[row-i][col-i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    copyBoard(temp, childGameBoard);
                    childBirth = true;
                    break;
                }
            }
            temp[row-i][col-i] = COLOR;
        }

        copyBoard(childGameBoard, temp);
        
        // bottom-right
        for (int i = 1; i <= Math.min(ROWS-1-row, COLS-1-col); i++) {
            if (gameBoard[row+i][col+i] == EMPTY) {
                break;
            }
            if (gameBoard[row+i][col+i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    copyBoard(temp, childGameBoard);
                    childBirth = true;
                    break;
                }
            }
            temp[row+i][col+i] = COLOR;
        }

        copyBoard(childGameBoard, temp);
        
        // top-right
        for (int i = 1; i <= Math.min(row, COLS-1-col); i++) {
            if (gameBoard[row-i][col+i] == EMPTY) {
                break;
            }
            if (gameBoard[row-i][col+i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    copyBoard(temp, childGameBoard);
                    childBirth = true;
                    break;
                }
            }
            temp[row-i][col+i] = COLOR;
        }

        copyBoard(childGameBoard, temp);
        
        // bottom-left
        for (int i = 1; i <= Math.min(ROWS-1-row, col); i++) {
            if (gameBoard[row+i][col-i] == EMPTY) {
                break;
            }
            if (gameBoard[row+i][col-i] == COLOR) {
                if (i == 1) {
                    break;
                } else {
                    copyBoard(temp, childGameBoard);
                    childBirth = true;
                    break;
                }
            }
            temp[row+i][col-i] = COLOR;
        }

        temp = null;
        if(!childBirth) {
            childGameBoard = null;
            return null;
        }

        childGameBoard[row][col] = COLOR; //played square changes color
        Board child = new Board(this, childGameBoard);
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

        return 50*cornerEval + 20*sideEval + allEval;
    }

    public void reset() {
        if (children != null) {
            children.clear();
            children = null;
        }

        setLastPlayer(-1*lastPlayer);
    }

    public String boardToString(Set<Move> availMoves){
        StringBuilder str = new StringBuilder("\t\t  1   2   3   4   5   6   7   8\n");

        String disk;

        for (int i = 0; i < ROWS; i++) {
            str.append("\t\t").append(i + 1).append(" ");

            for (int j = 0; j < COLS; j++) {
                if(gameBoard[i][j] != EMPTY)
                    disk = gameBoard[i][j] == BLACK ? BLACKDISK : WHITEDISK;
                else{
                    if (availMoves != null && availMoves.contains(new Move(i,j)))
                        disk = BALLDISK;    // user available moves
                    else
                        disk = EMPTYDISK;
                }
                str.append(disk).append("  ");
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

    public void clear() {
        if (children != null) {
    
            //children.clear();
            children = null;
        }

    }

    private static void copyBoard(int[][] source, int[][] destination) {
        for (int i = 0; i < ROWS; i++)
            System.arraycopy(source[i], 0, destination[i], 0, COLS);

    }

}