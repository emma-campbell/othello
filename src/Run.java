import java.util.Scanner;

import othello.game.Color;
import othello.game.Engine;
import othello.player.AI;
import othello.player.Human;
import othello.player.Player;
import othello.tui.Utils;

public class Run {

    static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {

        // This is a comment
        
        // TODO: Introduction
        
        // TODO: Choose game size
        int size = chooseGameSize();

        // TODO: Choose opponent
        int opp = chooseOpponent();

        Human p1 = new Human(Color.DARK);
        Player p2 = null;

        if (opp == 1) {
            System.out.println("UNHANDLED");
        } else if (opp == 2) {
            p2 = new AI(Color.LIGHT, 6, false);    
        } else {
            p2 = new AI(Color.LIGHT, 6, true);
        }
        
        if (p2 != null) {
            
            Engine engine = new Engine(size, p1, p2);
            
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
            System.out.println("\t3. MINIMAX with alpha-beta pruning");
            System.out.print("YOUR CHOICE [1, 2, 3]: ");

            if (input.hasNextInt()) {

                int choice = input.nextInt();

                if (choice == 1 || choice == 2 || choice == 3) {
                    return choice;
                }
            }

            Utils.clear();
        }
    }

    public static void presentWinner(Player winner, int winCount, int losCount) {

        String c = ((winner.color == Color.DARK) ? "DARK" : "LIGHT");

        Utils.clear();
        System.out.println("\n" + c + " IS THE WINNER");

    }
}
