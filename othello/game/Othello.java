package othello.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

import othello.game.exceptions.IllegalBoardDimensions;
import othello.player.Player;
import othello.tui.Utils;
import othello.ai.search.Problem;

/**
 * @author  Emma Campbell & Emma Schechter
 * @since   02-09-2021
 */
public class Othello implements Problem<Board, Point, Color> {
    
    private Scanner input = new Scanner(System.in);
    private Board board;
    
    private Player p1;
    private Player p2;

    private int p1Score;
    private int p2Score;

    /**
     * Constructor for the Othello game engine
     * 
     * @param dim square dimension of the board
     * @param p1  first player
     * @param p2  second player
     */
    public Othello(int dim, Player p1, Player p2) {

        try {
            this.board = new Board(dim, p1.color);
        } catch (IllegalBoardDimensions e) {
            System.out.println("ERROR: " + e.toString());
        }

        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Determine the winner of the game
     * 
     * @return the player who won the game
     */
    private Player decideWinner() {

        int p1Score = board.getPlayerPeices(p1.color);
        int p2Score = board.getPlayerPeices(p2.color);

        if (p1Score == p2Score) {
            return null;
        } else if (p1Score > p2Score) {
            return p1;
        } else {
            return p2;
        }
    }

    /**
     * Get the score of the given player color
     * 
     * @param c color of the player to get the score of
     * @return score of the player with the given color
     */
    public int getScore(Color c) {
        return board.getPlayerPeices(c);
    }

    /**
     * This code actually drives the game play. It switches the turns, gets the
     * users move, and calls the methods that generate the AI's move. When game play
     * is finished, it calls {@code decideWinner()} to determine who won the game
     * 
     * @return      {@code Player} who won
     */
    public Player play() {

        String p1Color = ((p1.color == Color.DARK) ? "DARK" : "LIGHT"); // Store P1's color
        String p2Color = ((p2.color == Color.DARK) ? "DARK" : "LIGHT"); // Store P2's color

        do {

            // Calling this to clear the screen and create more
            // intriguing game play.
            Utils.clear();

            System.out.println("X = DARK, O = LIGHT\n");
            printBoard();

            // First check that the board has moves remaining
            if (board.hasAnyMoves(p1.color) || board.hasAnyMoves(p2.color)) {
                // Then, determine who's turn it is
                if (board.whoseTurn() == p1.color) {
                    // Double check that that player has moves remaining
                    if (board.hasAnyMoves(p1.color)) {
                        // If they are a user
                        if (p1.isUserPlayer()) {

                            takeTurn(p1);

                        } else { // Otherwise, they are AI! (or Random)
                            Point move = p1.play(this);
                            board.refreshBoard(move, p1.color);
                            Utils.wait(3000);
                        }
                    } else {

                        // In this instance, there are no actual moves that p1
                        // can play, so we inform the user and then set the turn
                        // to the other player.

                        System.out.println("\n" + p1Color + " HAS NO LEGAL MOVES.");
                        board.setTurn();

                        // This is just so the user has time to read and understand
                        // that there are no moves for them.

                        Utils.wait(3);

                    }
                } else {
                    // Check to see that p2 has moves left on the board
                    if (board.hasAnyMoves(p2.color)) {
                        // Check to see if they are a user player
                        if (p2.isUserPlayer()) {

                            takeTurn(p2);

                        } else { // The player is AI! (or Random)
                            Point move = p2.play(this);
                            board.refreshBoard(move, p2.color);
                            Utils.wait(3000);
                        }
                    } else {

                        // In this instance, there are no actual moves that p1
                        // can play, so we inform the user and then set the turn
                        // to the other player.

                        System.out.println("\n" + p2Color + " HAS NO LEGAL MOVES.");
                        board.setTurn();

                        // This is just so the user has time to read and understand
                        // that there are no moves for them.

                        Utils.wait(3);
                    }
                }
            }
        } while (!(board.isGameFinished()));

        input.close();
        return decideWinner();
    }

    /**
     * Have the USER take their turn
     * 
     * @param p     user {@code Player}
     */
    private void takeTurn(Player p) {
        
        Point move;

        String colorStr = (p.color == Color.DARK ? "DARK" : "LIGHT");

        do {
            System.out.print("\n" + colorStr + ", PLEASE CHOSE A SPACE [rowcol (i.e. a3)]: ");
            String space = input.next();

            move = stringToPoint(space);

            if (board.validMove(p.color, move.x, move.y)) {
                break;
            }

        } while (!board.validMove(p.color, move.x, move.y));

        // Add our new point for p
        board.refreshBoard(move, p.color);
    }

    /**
     * Converts a string input such as 'a4' into a {@code Point}
     * 
     * @param input     input string
     * @return          Point corresponding to the input string
     */
    private Point stringToPoint(String input) {

        int row = 0;
        int col = 0;

        char colVal = input.charAt(0);
        char rowVal = input.charAt(1);

        char[] cols = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };

        for (int i = 0; i < board.dim; i++) {
            if (cols[i] == colVal) {
                col = i;
            }
        }

        row = Character.getNumericValue(rowVal);

        return new Point(row - 1, col);
    }

    /**
     * Prints the board to Std. Out.
     */
    public void printBoard() {
        System.out.print(board.toString());
    }

    /**
     * Returns all the possible actions that can be made by the player whose turn it
     * is
     * 
     * @param s     {@code Board} currently in play
     * @return      {@code ArrayList<Point>} of all possible moves
     */
    @Override
    public ArrayList<Point> actions(Board s) {
        return s.getAllPossibleMoves(s.whoseTurn());
    }

    /**
     * Generates the "result"-ing Board when {@code Point} move is applied to the
     * current {@code Board} s.
     * 
     * @param s     current {@code Board}
     * @param move  move to be applied
     * @return      resulting board
     */
    @Override
    public Board result(Board s, Point move) {
        return s.getNextBoard(move, s.whoseTurn());
    }

    /**
     * Is the given board a terminal state?
     * 
     * @param s     current board
     * @return      {@code true} if terminal state, {@code false} otherwise.
     */
    @Override
    public boolean isTerminal(Board s) {
        return s.isTerminal();
    }

    /**
     * Utility function that determines whether the given player p won or not
     * 
     * @param s     current board
     * @param p     current player
     * @return      {@code 1} if player won, {@code 0} if tied, {@code -1} if lost
     */
    @Override
    public int utility(Board s, Color p) {
        Color otherPlayer = (p == Color.DARK ? Color.LIGHT : Color.DARK);

        p1Score = s.getPlayerPeices(p);
        p2Score = s.getPlayerPeices(otherPlayer);

        if (p1Score > p2Score) {
            return 1;
        } else if (p1Score == p2Score) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Returns the "initial state" for search problem
     * 
     * @return      the current board
     */
    @Override
    public Board initialState() {
        return this.board;
    }

    /**
     * Whose turn is it in the given state?
     * 
     * @param s     current board
     * @return      {@code Color} representing whose turn it is
     */
    @Override
    public Color whoseTurn(Board s) {
        return s.whoseTurn();
    }

    @Override
    public int heuristic(Board s) {
        Color player = s.whoseTurn();
        int mobility = mobility(s, player);
        int discDiff = discDifference(s, player);
        return 2*mobility + discDiff + 1000*corners(s, player);
    }

    /**
     * Evaluates the difference in the number of disks between AI and the other player
     * 
     * @param b         {@code Board} with current state
     * @param player    {@code Color} representing the current player
     * @return          integer difference between the numbers of discs
     */
    private int discDifference(Board b, Color player) {
        Color otherPlayer = ((player == Color.DARK) ? Color.LIGHT : Color.DARK);

        int discCount = b.getPlayerPeices(player);
        int otherDiscCount = b.getPlayerPeices(otherPlayer);

        return 100 * (discCount - otherDiscCount) / (discCount + otherDiscCount + 1);
    }

    /**
     * Evaluates the mobility (ability to move) of the given player
     * 
     * @param b         {@code Board} with current state
     * @param player    {@code Color} representing the current player
     * @return          integer difference in mobility between the two players
     */
    private int mobility(Board b, Color player) {
        Color otherPlayer = ((player == Color.DARK) ? Color.LIGHT : Color.DARK);
        
        int moveCount = b.getAllPossibleMoves(player).size();
        int otherMoveCount = b.getAllPossibleMoves(otherPlayer).size();
        
        return 100 * (moveCount - otherMoveCount) / (moveCount + otherMoveCount + 1);  
    }

    /**
     * Evaluates the number of corners captured by the given player. Corners are important
     * because once captured, the opponent cannot flip them.
     * 
     * @param b         {@code Board} of current state
     * @param player    {@code Color} representing the current player
     * @return          integer difference between the corners of the two players
     */
    private static int corners(Board b, Color player) {
        
        Color otherPlayer = ((player == Color.DARK) ? Color.LIGHT : Color.DARK);

        int corners = 0;
        int otherCorners = 0;

        if (b.get(0, 0) == player) corners++;
        if (b.get(b.dim-1, 0) == player) corners++;
        if (b.get(0, b.dim-1) == player) corners++;
        if (b.get(b.dim-1, b.dim-1) == player) corners++;

        if (b.get(0, 0) == otherPlayer) otherCorners++;
        if (b.get(b.dim-1, 0) == otherPlayer) otherCorners++;
        if (b.get(0, b.dim-1) == otherPlayer) otherCorners++;
        if (b.get(b.dim-1, b.dim-1) == otherPlayer) otherCorners++;

        return 100 * (corners - otherCorners) / (corners + otherCorners + 1);
    }
}
