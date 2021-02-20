package othello.ai.algorithms;

/**
 * @author  Emma Campbell & Emma Schechter
 * @since   02-14-2021
 */
public interface SearchAlgorithm<State, Action> {
    
    /**
     * Generic algorithm in which a state is supplied, and the minimax 
     * algorithm is implemented. 
     * 
     * @param s     state the game is currently in
     * @return      next desirable action 
     */
    public Action solve(State s);
    
}
