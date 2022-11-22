import java.util.Scanner;

public class Reversi {

    private static final Player human = new Human();
    private static final Player machine = new Machine();
    private static final Player machine_2 = new Machine(); //serves for AI-vs-AI games, testing purposes

    private static Player currentPlayer;

    private static boolean testAI = false;

    public static void main(String[] args){
        try {
            if (args[0].equals("-testai")) {
                testAI = true;
            }
        } catch (Exception ignored) {}

        Board board = new Board();

        Scanner input = new Scanner(System.in);
        int difficulty;
        String option;

        //"REVERSI"
        printReversiBanner();

        if (!testAI) {
            System.out.println("\tWhat's your name? ");
            System.out.print("\t\t > ");
            String name = input.next();

            human.setName(name);
            System.out.println("\n\tWelcome, "+human.getName()+"!");
        }
        else {
            System.out.println("\tAI vs AI testing mode...\n");
            machine_2.setName("R2-D2");
        }
        machine.setName("WALL-E");


        // CHOOSE DIFFICULTY OPTION
        do {
            System.out.print("\n\tChoose difficulty (>1): ");
            //difficulty = input.next().charAt(0) - '0';
            difficulty = 0;
            if (input.hasNextInt())
                difficulty = input.nextInt();
            else {
                input.next();
            }
        } while (difficulty < 2);

        ((Machine)machine).setMaxDepth(difficulty);
        if (testAI) { ((Machine)machine_2).setMaxDepth(difficulty); }

        // CHOOSE WHO PLAYS FIRST
        if (!testAI) {
            do {
                System.out.print("\n\tChoose \n\t\t1. You play first \n\t\t2. " + machine.getName() + " plays first \n\tSelection: ");
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

            } while (!(option.equals("1") || option.equals("2")));
        }
        else {
            machine.setColor(Board.BLACK);
            machine_2.setColor(Board.WHITE);
            currentPlayer = machine;
        }

        board.setLastPlayer(Board.WHITE);       //Black plays first

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

        // Winning board
        System.out.println("\n\tWinning Board!");
        machine.printBoard(board);

        // Calc score and winner
        System.out.println("\n\tGAME OVER!");
        int score = board.calcScore();
        int winner = board.getWinner();

        // Print winner
        Player foe = (testAI) ? machine_2 : human;
        if(winner == foe.getColor())
            System.out.print("\t\t"+foe+" wins! :\t");
        else if(winner == machine.getColor())
            System.out.print("\t\t"+machine+" wins! :\t");
        else
            System.out.print("It's a tie! :\t");

        // Print score
        if(winner == Board.BLACK) {
            System.out.println((64 - score) + " - " + score + "\n");
        }else if (winner == Board.WHITE)
            System.out.println( score+" - "+(64-score) + "\n");
        else
            System.out.println( score +" - "+score + "\n");

    }

    private static void switchPlayer() {
        if (testAI) {
            currentPlayer = (currentPlayer == machine) ? machine_2 : machine;
        }
        else {
            currentPlayer = (currentPlayer == machine) ? human : machine;
        }
    }

    private static void printReversiBanner() {
        System.out.println("""


                 ______    _______  __   __  _______  ______    _______  ___ \s
                |    _ |  |       ||  | |  ||       ||    _ |  |       ||   |\s
                |   | ||  |    ___||  |_|  ||    ___||   | ||  |  _____||   |\s
                |   |_||_ |   |___ |       ||   |___ |   |_||_ | |_____ |   |\s
                |    __  ||    ___||       ||    ___||    __  ||_____  ||   |\s
                |   |  | ||   |___  |     | |   |___ |   |  | | _____| ||   |\s
                |___|  |_||_______|  |___|  |_______||___|  |_||_______||___|\s

                """);
    }

    private static void printStartBanner(){
        System.out.println("""


                \t       __                    __     \s
                \t      /\\ \\__                /\\ \\__  \s
                \t  ____\\ \\ ,_\\    __     _ __\\ \\ ,_\\ \s
                \t /',__\\\\ \\ \\/  /'__`\\  /\\`'__\\ \\ \\/ \s
                \t/\\__, `\\\\ \\ \\_/\\ \\L\\.\\_\\ \\ \\/ \\ \\ \\_\s
                \t\\/\\____/ \\ \\__\\ \\__/.\\_\\\\ \\_\\  \\ \\__\\
                \t \\/___/   \\/__/\\/__/\\/_/ \\/_/   \\/__/
                \t                                    \s
                \t                                     \s
                """);
    }
}