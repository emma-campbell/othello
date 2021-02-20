package othello.ai.search;

/**
 * @author  Emma Campbell & Emma Schechter
 * @since   02-14-2021
 */
public interface State<Player> {
    
    /**
     * Is the current state a terminal state?
     * 
     * @return  {@code true} if terminal, {@code false} otherwise
     */
    public boolean isTerminal();

    /**
     * Whose turn is it in the given state?
     * 
     * @return  player whose turn it currently is
     */
    public Player whoseTurn();
    
}
