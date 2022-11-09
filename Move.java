public class Move{
    private int row;
    private int col;

    Move() {
        row = -1;
        col = -1;
    }

    Move(int row, int col) {
        makeMove(row, col);
    }

    public void makeMove(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {return row;}
    public int getCol() {return col;}

    @Override
    public boolean equals(Object check) {
        if (check == null)
            return false;

        if (!(check instanceof Move))
            return false;

        if (check == this)
            return true;

        Move m = (Move) check;

        return (row == m.row && col == m.col);
    }

    public int hashCode(){
        return row+col;
    }
}