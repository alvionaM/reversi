import java.util.ArrayList;

public abstract class Player {
    protected int color;
    //protected ArrayList<Board> children;

    public boolean canMove(Board board)
    {
        return !board.getChildren().isEmpty();
    }

    public void produceMoves(Board board)
    {
        board.produceChildren();
        //children = board.getChildren();
    }

    public abstract Board play(Board board);

    public int getColor(){
        return color;
    }

    public void setColor(int color){
        this.color = color;
    }
}
