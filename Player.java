public abstract class Player {
    protected int color;

    public boolean canMove(Board board)
    {
        return !board.getChildren().isEmpty();
    }

    public void produceMoves(Board board)
    {
        board.produceChildren();
    }

    public abstract Board play(Board board);

    public int getColor(){
        return color;
    }

    public void setColor(int color){
        this.color = color;
    }
}
