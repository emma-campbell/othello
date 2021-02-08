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

        Human player1 = new Human(Color.DARK);
        AI player2 = new AI(Color.LIGHT, 10, true);

        Engine engine = new Engine(8, player1, player2);
        Player winner = engine.play();

        // TODO: Introduction
        
        // TODO: Choose game size
        // int size = chooseGameSize();

        // TODO: Choose opponent
        // int opp = chooseOpponent();

        // TODO: Choose a search cutoff
        // if (opp != 1) {
        //     int cutoff = chooseSearchCutoff();
        // }
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
            System.out.println("\t1. Random Player")
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

    public static int chooseSearchCutoff() {

        while (true) {
            System.out.println("\nPLEASE CHOOSE A SEARCH CUTOFF. KEEP IN MIND THE HIGHER THE NUMBER, THE SLOWER I WILL THINK...");
            System.out.println("YOUR CHOICE [any number]: ");
            
            if (input.hasNextInt()) {
                return input.nextInt();
            }

            Utils.clear();
        }
    }
}
