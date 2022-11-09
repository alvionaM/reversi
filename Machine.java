import java.util.ArrayList;
import java.util.Random;

public class Machine extends Player{

    protected int maxDepth;

    Machine(int maxDepth){
        this.maxDepth = maxDepth;
    }

    @Override
    public Board play(Board board){
        return MiniMax(board);
    }

    Board MiniMax(Board board) {
        if(color == Board.BLACK)
        {
            //If machine plays as black it wants to maximize the heuristics value
            max(board, 0);
        }
        else
        {
            //If machine plays as white it wants to minimize the heuristics value
            min(board, 0);
        }

        //############# Test ###############
        for(Board child: board.getChildren()){
            System.out.println(child.getBestExpectedValue());
            System.out.println();
        }//##################################

        Random r = new Random();
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
        for (; i < children.size(); i++) {
            int val = children.get(i).getBestExpectedValue();
            if(val == board.getBestExpectedValue() && r.nextInt(2) == 1) {
                keptChild = children.get(i);
            }
        }
        return new Board(keptChild);
    }

    int max(Board board, int depth){
        if(depth == maxDepth || board.isTerminal()) //if reached maxDepth, children won't be produced in isTerminal()
        {
            //############# Test ###############
            //System.out.println("k = "+depth+" From max: "+board.evaluate());
            //board.print();
            //System.out.println();
            //##################################

            int evaluation = board.evaluate();
            board.setBestExpectedValue(evaluation);
            return evaluation;

        }
        int maxValue = Integer.MIN_VALUE;
        for(Board child: board.getChildren()){
            int childValue = min(child, depth+1);
            if(childValue > maxValue){
                maxValue = childValue;
            }

        }
        board.setBestExpectedValue(maxValue);
        return maxValue;
    }

    int min(Board board, int depth){
        if(depth == maxDepth || board.isTerminal()) //if reached maxDepth, children won't be produced in isTerminal()
        {
            //############# Test ###############
            //System.out.println("k = "+depth+" From min: "+board.evaluate());
            //board.print();
            //System.out.println();
            //##################################

            int evaluation = board.evaluate();
            board.setBestExpectedValue(evaluation);
            return evaluation;
        }
        int minValue = Integer.MAX_VALUE;
        for(Board child: board.getChildren()){
            int childValue = max(child, depth+1);
            if(childValue < minValue){
                minValue = childValue;
            }

        }
        board.setBestExpectedValue(minValue);
        return minValue;
    }
}