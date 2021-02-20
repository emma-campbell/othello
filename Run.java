import java.util.Scanner;

import othello.game.Color;
import othello.game.Othello;
import othello.player.AI;
import othello.player.Human;
import othello.player.Computer;
import othello.player.Player;
import othello.tui.Utils;

public class Run {

    static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {

        Utils.clear();
        
        System.out.println("Welcome to Othello!");
        int size = chooseGameSize();
        Utils.clear();

        int opp = chooseOpponent();
        Utils.clear();
        
        Human p1 = new Human(Color.DARK);
        Player p2 = null;
        
        if (opp == 1) {
            p2 = new Computer(Color.LIGHT);
        } else {
            if (opp != 2) {
                int limit = chooseDepthLimit();
                p2 = new AI(Color.LIGHT, opp, limit);
            } else {
                p2 = new AI(Color.LIGHT, opp, 0);
            }
        }

        if (p2 != null) {
            
            Othello engine = new Othello(size, p1, p2);
            
            Player winner = engine.play();
            Color loser = ((winner.color == Color.DARK) ? Color.LIGHT : Color.DARK);

            int winScore = engine.getScore(winner.color);
            int losScore = engine.getScore(loser);

            presentWinner(winner, winScore, losScore);
        }        
    }

    public static int chooseGameSize() {
        while (true) {
            
            System.out.print("\nPLEASE CHOOSE A GAME SIZE [4, 6, 8]: ");

            if (input.hasNextInt()) {

                int choice = input.nextInt();
                
                if (choice == 4 || choice == 6 || choice == 8) {
                    return choice;
                }
            }

            Utils.clear();
        }
    }

    public static int chooseOpponent() {

        while (true) {

            System.out.println("\nPLEASE CHOOSE AN OPPONENT FROM THE FOLLOWING OPTIONS...");
            System.out.println("\t1. Random Player");
            System.out.println("\t2. MINIMAX");
            System.out.println("\t3. H-MINIMAX");
            System.out.println("\t4. H-MINIMAX with alpha-beta pruning");

            System.out.print("YOUR CHOICE [1, 2, 3, 4]: ");

            if (input.hasNextInt()) {

                int choice = input.nextInt();
                
                if (choice == 1 || choice == 2 || choice == 3 || choice == 4) {
                    return choice;
                }
            }

            Utils.clear();
        }
    }

    public static int chooseDepthLimit() {
        while (true) {
            System.out.println("Please select a limit to the depth-first search.");
            System.out.println("Keep in mind, that the larger the number, the longer the search will take.");
            System.out.println("I recommend chosing a limit between 1 and 9.");
            System.out.print("DEPTH LIMIT: ");

            if (input.hasNextInt()) {
                return input.nextInt();
            }

            Utils.clear();
        }
    }

    public static void presentWinner(Player winner, int winCount, int losCount) {

        if (winner != null) {
            String c = ((winner.color == Color.DARK) ? "DARK" : "LIGHT");
            String otherC = ((c.equals("DARK")) ? "LIGHT" : "DARK");
    
            Utils.clear();
            
            System.out.println(c + " IS THE WINNER\n");
            System.out.println(c + ": " + winCount);
            System.out.println(otherC + ": " + losCount);
        } else {
            System.out.println("It's a tie!");
        }

    }
}