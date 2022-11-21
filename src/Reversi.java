import java.util.Scanner;

public class Reversi {

    private static final Player human = new Human();
    private static final Player machine = new Machine();
    //private static final Player machineNr2 = new Machine(); //serves for AI-vs-AI games, testing purposes

    private static Player currentPlayer;


    public static void main(String[] args){

        Board board = new Board();

        Scanner input = new Scanner(System.in);
        int difficulty;
        String option;

        //"REVERSI"
        printReversiBanner();

        System.out.println("\tWhat's your name? ");
        System.out.print("\t\t > ");
        String name = input.next();

        human.setName(name);
        machine.setName("WALL-E");

        System.out.println("\n\tWelcome, "+human.getName()+"!");

        do {
            System.out.print("\n\tChoose difficulty (2-8): ");
            difficulty = input.next().charAt(0) - '0';
        } while (difficulty < 2 || difficulty > 8);

        ((Machine)machine).setMaxDepth(difficulty);

        do {
            System.out.print("\n\tChoose \n\t\t1. You play first \n\t\t2. "+machine+" plays first \n\tSelection: ");
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


        System.out.println("\n");
        machine.printBoard(board);
        do{
            System.out.print("\tAre you ready to play? y/n: ");
            option = input.next();
        }while(!option.equals("y"));

        //"START"
        printStartBanner();



        //Starts actual game
        int terminate = 0;

        while(terminate < 2){

            currentPlayer.produceMoves(board);

            if(currentPlayer.canMove(board)) {
                terminate = 0;

                System.out.println("\tPLAYS: "+currentPlayer);
                currentPlayer.printBoard(board);

                board = currentPlayer.play(board);


                System.out.println("\t-------------------------------------------------\n");

                //Testing
                //currentPlayer.printBoard(board);

            }
            else {
                board.reset();
                terminate++;

                System.out.println("\t"+currentPlayer+" could not move!");
            }
            
            switchPlayer();
        }

        //Winning board
        System.out.println("---Winning Board---");
        machine.printBoard(board);

        System.out.println("\n\tGAME OVER!");
        int score = board.calcScore();
        int winner = board.getWinner();


        if(winner == human.getColor())
            System.out.print("\t\t"+human+" wins! :\t");
        else if(winner == machine.getColor())
            System.out.print("\t\t"+machine+" wins! :\t");
        else
            System.out.print("It's a tie! :\t");


        if(winner == Board.BLACK) {
            System.out.println((64 - score) + " - " + score + "\n");
        }else if (winner == Board.WHITE)
            System.out.println( score+" - "+(64-score) + "\n");
        else
            System.out.println( score +" - "+score + "\n");

    }

    private static void switchPlayer() {
        if (currentPlayer == human)
            currentPlayer = machine;
        else
            currentPlayer = human;
    }

    private static void printReversiBanner() {
        System.out.println("\n\n" +
                " ______    _______  __   __  _______  ______    _______  ___  \n" +
                "|    _ |  |       ||  | |  ||       ||    _ |  |       ||   | \n" +
                "|   | ||  |    ___||  |_|  ||    ___||   | ||  |  _____||   | \n" +
                "|   |_||_ |   |___ |       ||   |___ |   |_||_ | |_____ |   | \n" +
                "|    __  ||    ___||       ||    ___||    __  ||_____  ||   | \n" +
                "|   |  | ||   |___  |     | |   |___ |   |  | | _____| ||   | \n" +
                "|___|  |_||_______|  |___|  |_______||___|  |_||_______||___| \n\n");
    }

    private static void printStartBanner(){
        System.out.println("\n\n" +
                "\t       __                    __      \n" +
                "\t      /\\ \\__                /\\ \\__   \n" +
                "\t  ____\\ \\ ,_\\    __     _ __\\ \\ ,_\\  \n" +
                "\t /',__\\\\ \\ \\/  /'__`\\  /\\`'__\\ \\ \\/  \n" +
                "\t/\\__, `\\\\ \\ \\_/\\ \\L\\.\\_\\ \\ \\/ \\ \\ \\_ \n" +
                "\t\\/\\____/ \\ \\__\\ \\__/.\\_\\\\ \\_\\  \\ \\__\\\n" +
                "\t \\/___/   \\/__/\\/__/\\/_/ \\/_/   \\/__/\n" +
                "\t                                     \n" +
                "\t                                      \n");
    }
}