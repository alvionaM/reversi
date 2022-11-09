public abstract class Player {
    private int color;
    private String name;

    public Player() {
        this.name = "Unknown";
    }


    public boolean canMove(Board board)
    {
        return !board.getChildren().isEmpty();
    }

    public void produceMoves(Board board)
    {
        board.produceChildren();
    }

    public abstract void printBoard(Board board);

    public abstract Board play(Board board);

    public int getColor(){
        return color;
    }

    public void setColor(int color){
        this.color = color;
    }

    public void setName(String name){
        this.name = name;
    }

    public String toString(){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
