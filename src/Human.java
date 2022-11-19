import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

public class Human extends Player{

    Scanner input = new Scanner(System.in);
    HashMap<Move, Board> availMoves = new HashMap<>();

    @Override
    public void produceMoves(Board board){
        super.produceMoves(board);

        availMoves.clear();
        for (Board child : board.getChildren()) {
            availMoves.put(child.lastMove, child);
        }
    }

    @Override
    public Board play(Board board){

        System.out.println("\tAvailable moves: ");
        for (Move availMove : availMoves.keySet()) {
            System.out.println("\t"+availMove);
        }

        System.out.println();
        Move chosenMove = new Move();
        if (!availMoves.isEmpty()) {
            do {
                System.out.print("\tEnter row: ");
                int r = input.next().charAt(0) - '1';
                System.out.print("\tEnter col: ");
                int c = input.next().charAt(0) - '1';
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

    @Override
    public void printBoard(Board board){
        PrintWriter printWriter = new PrintWriter(System.out,true, StandardCharsets.UTF_8);
        printWriter.println(board.boardToString(availMoves.keySet()));
    }
}
