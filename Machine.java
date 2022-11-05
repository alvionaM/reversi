public class Machine extends Player{

    protected int maxDepth;

    Machine(int maxDepth){
        this.maxDepth = maxDepth;
    }
    public Board play(Board board){
        return MiniMax(board);
    }

    Board MiniMax(Board board)
    {
        if(color == Board.BLACK)
        {
            //If machine plays as black then it wants to maximize the heuristics value
            max(board, 0);
        }
        else
        {
            //If machine plays as white then it wants to minimize the heuristics value
            min(board, 0);
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for(Board child: board.getChildren()){
            System.out.println(child.getBestExpectedValue());
            System.out.println();
        }

        for(Board child: board.getChildren()){
            if(child.getBestExpectedValue() == board.getBestExpectedValue())
                return new Board(child); //?????????????????????????????
        }
        return null; //??????????????????????????????
    }

    int max(Board board, int depth){
        if(board.isTerminal() || depth == maxDepth)
        {
            //System.out.println("From max: "+board.evaluate());
            return board.evaluate();

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
        if(board.isTerminal() || depth == maxDepth)
        {
            return board.evaluate();
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
