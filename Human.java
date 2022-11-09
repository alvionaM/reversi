import java.util.HashMap;
import java.util.Scanner;

public class Human extends Player{

    Scanner input = new Scanner(System.in);
    int r, c;

    @Override
    public Board play(Board board){

        HashMap<Move, Board> availMoves = new HashMap<>();

        System.out.println("Available moves: ");
        for (Board child : board.getChildren()) {
            //availMoves.add(child.lastMove);
            availMoves.put(child.lastMove, child);
            System.out.println("("+(child.lastMove.getRow()+1)+", "+(child.lastMove.getCol()+1)+")");
        }

        System.out.println();
        Move chosenMove = new Move();
        if (!availMoves.isEmpty()) {
            do {
                System.out.print("Enter row: ");
                r = input.nextInt() - 1;
                System.out.print("Enter col: ");
                c = input.nextInt() - 1;
                chosenMove.makeMove(r, c);

            } while (!availMoves.containsKey(chosenMove));
            System.out.println();
        }
        else {
            return null;
        }

        Board playedMove = availMoves.get(chosenMove);

        return new Board(playedMove);
    }
}
