package othello.game.exceptions;

/**
 * @author  Emma Campbell & Emma Schechter
 * @since   02-09-2021
 */
public class IllegalMove extends Exception {

    private static final long serialVersionUID = 1L;
    
    public IllegalMove() {
        super("Invalid Move");
    }
}