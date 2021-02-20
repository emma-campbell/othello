package othello.player;

import java.awt.Point;

import othello.ai.search.Problem;
import othello.game.Board;
import othello.game.Color;

/**
 * @author  Emma Campbell & Emma Schechter
 * @since   02-09-2021
 */
public abstract class Player {
    
    public Color color;

    /**
     * Constructor for the Player Class
     * 
     * @param c     {@code Color} representing which player
     */
    protected Player(Color c) {
        this.color = c;
    }
    
    /**
     * Is the player a user or non-user?
     * 
     * @return          {@code true} if a user player, {@code false} otherwise.
     */
    abstract public boolean isUserPlayer();

    /**
     * Play the next move 
     * 
     * @param problem   generic instance of {@code Problem}
     * @return          {@code Point} next move
     */
    abstract public Point play(Problem<Board, Point, Color> problem);
}