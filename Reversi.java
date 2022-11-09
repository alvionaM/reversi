import java.util.Scanner;

public class Reversi {

    private static final Player human = new Human();
    private static final Player machine = new Machine(6);
    private static Player currentPlayer;


    public static void main(String[] args){

        Board board = new Board();

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


        //board.print();

        int terminate = 0;

        while(terminate < 2){

            board.print();

            currentPlayer.produceMoves(board);

            if(currentPlayer.canMove(board)) {
                terminate = 0;

                //??????????
                System.out.println("PLAYS: "+currentPlayer.getColor());
                board = currentPlayer.play(board);

                //board.print();
                System.out.println("--------------------------\n");

                //Testing
                //break;

            }
            else {
                board.reset();
                terminate++;

                //????????
                System.out.println("Player: "+currentPlayer.getColor()+" could not move");
            }
            
            switchPlayer();
            //System.out.println("Switched");
        }
        System.out.println("GAME OVER");
    }

    private static void switchPlayer() {
        if (currentPlayer == human)
            currentPlayer = machine;
        else
            currentPlayer = human;
    }
}
