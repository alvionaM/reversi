import java.util.ArrayList;
import java.util.Scanner;

public class Reversi {

    public static void main(String[] args){

        int maxDepth = 1;

        Board board = new Board();
        Human human = new Human();
        Machine machine = new Machine(maxDepth);

        Player[] players = new Player[2];
        players[0] = human;
        players[1] = machine;
        Player currentPlayer;

        Scanner input = new Scanner(System.in);
        String option;


        do {
            System.out.print("Choose \n\t1. To play first \n\t2. To let computer play first \nSelection: ");
            option = input.next();

            if (option.equals("1")) {
                human.setColor(Board.BLACK);       //Black plays first, so human gets Black
                machine.setColor(Board.WHITE);     //Machine gets white
                currentPlayer = human;
            } else {
                machine.setColor(Board.BLACK);     //Black plays first, so machine gets Black
                human.setColor(Board.WHITE);       //Human gets white
                currentPlayer = machine;
            }

            board.setLastPlayer(Board.WHITE);       //Black plays first


        }while(!(option.equals("1") || option.equals("2")));




        int terminate = 0;

        while(terminate < 2){

            board.print();

            currentPlayer.produceMoves(board);

            if(currentPlayer.canMove(board)) {
                terminate = 0;

                /*if (board.getLastPlayer() == human.getColor()) {
                    board = currentPlayer.play(board);
                    currentPlayer = human;
                } else {
                    currentPlayer.play(board);
                    currentPlayer = machine;
                }*/
                board = currentPlayer.play(board);

                board.print();
                System.out.println("--------------------------");

                System.out.println();

                //Testing
                break;

            }else{
                terminate++;
            }

            currentPlayer = switchPlayer(currentPlayer, players);
            System.out.println("Switched");
        }

    }

    private static Player switchPlayer(Player currentPlayer, Player[] players){
        if(currentPlayer==players[0]){
            return players[1];
        }else{
            return players[0];
        }
    }
}
