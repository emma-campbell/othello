package othello.player.ai;

import othello.game.Board;
import othello.game.Color;

/**
 * 
 */
public class Heuristics {
    
    public static int eval(Board b, Color player) {
        int mobility = mobility(b, player);
        int discDiff = discDifference(b, player);
        return 2*mobility + discDiff + 1000*corners(b, player);
    }

    /**
     * Evaluates the difference in the number of disks between AI and the other player
     * 
     * @param b         {@code Board} with current state
     * @param player    {@code Color} representing the current player
     * @return          integer difference between the numbers of discs
     */
    public static int discDifference(Board b, Color player) {
        
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
    public static int mobility(Board b, Color player) {
        
        Color otherPlayer = ((player == Color.DARK) ? Color.LIGHT : Color.DARK);
        
        int moveCount = b.getAllPossibleMoves(player).size();
        int otherMoveCount = b.getAllPossibleMoves(otherPlayer).size();
        
        return 100 * (moveCount - otherMoveCount) / (moveCount + otherMoveCount + 1);
    }
    
    /**
     * Evaluates the number of corners captured by the given player. Corners are important
     * because once captured, the opponent cannot flip them.
     * 
     */
    public static int corners(Board b, Color player) {
        
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
