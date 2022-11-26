import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class Machine extends Player{

    private final PrintWriter printWriter = new PrintWriter(System.out,true, StandardCharsets.UTF_8);

    private int maxDepth;

    Machine(int maxDepth){
        this.maxDepth = maxDepth;
    }

    Machine() {}

    @Override
    public void printBoard(Board board){
        printWriter.println(board.boardToString(null));
    }

    @Override
    public Board play(Board board){

        Collections.shuffle(board.getChildren());
        Board newBoard = MiniMax(board);
        
        printWriter.println("\t\tGonna play at... "+newBoard.lastMove);
        
        return newBoard;

    }

    private Board MiniMax(Board board) {
        if(this.getColor() == Board.BLACK)
        {
            //If machine plays as black it wants to maximize the heuristics value
            max(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        else
        {
            //If machine plays as white it wants to minimize the heuristics value
            min(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        Board keptChild = new Board();
        ArrayList<Board> children = board.getChildren();
        int i;
        for(i = 0; i < children.size(); i++){
            int val = children.get(i).getBestExpectedValue();
            if(val == board.getBestExpectedValue()) {
                keptChild = children.get(i);
                break;
            }
        }

        board.clear();
        
        return new Board(keptChild);
    }

    private int max(Board board, int depth, int a, int b){
        if(depth == maxDepth || board.isTerminal()) //if reached maxDepth, children won't be produced in isTerminal()
        {
            int evaluation = board.evaluate();
            board.setBestExpectedValue(evaluation);
            return evaluation;
        }
        
        int maxValue = Integer.MIN_VALUE;
        for(Board child: board.getChildren()){
            // Set depth
            int newDepth = (board.lastMove.equals(child.lastMove) ? depth : depth+1 );
            // depth+1 normally, depth if turn is lost

            int childValue = min(child, newDepth, a, b);
            if(childValue > maxValue){
                maxValue = childValue;
            }

            //Pruning
            if(maxValue >= b) {
                board.setBestExpectedValue(maxValue);

                if (depth>1) {
                    board.clear();
                }
                return maxValue;
            }
            a = Math.max(a, maxValue);
        }

        board.setBestExpectedValue(maxValue);

        if (depth>1) {
            board.clear();
        }
        return maxValue;
    }

    private int min(Board board, int depth, int a, int b){
        if(depth == maxDepth || board.isTerminal()) //if reached maxDepth, children won't be produced in isTerminal()
        {
            int evaluation = board.evaluate();
            board.setBestExpectedValue(evaluation);
            return evaluation;
        }
        
        int minValue = Integer.MAX_VALUE;
        for(Board child: board.getChildren()){
            // Set depth
            int newDepth = (board.lastMove.equals(child.lastMove) ? depth : depth+1 );
            // depth+1 normally, depth if turn is lost

            int childValue = max(child, newDepth, a, b);
            if(childValue < minValue){
                minValue = childValue;
            }

            //Pruning
            if(minValue <= a) {
                board.setBestExpectedValue(minValue);

                if (depth>1) {
                    board.clear();
                }
                return minValue;
            }
            b = Math.min(b, minValue);
        }
        
        board.setBestExpectedValue(minValue);

        if (depth>1) {
            board.clear();
        }
        return minValue;
    }

    public void setMaxDepth(int depth) { maxDepth = depth; }
}