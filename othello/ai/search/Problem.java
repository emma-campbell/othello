package othello.ai.search;

import java.util.ArrayList;

/**
 * @author  Emma Campbell & Emma Schechter
 * @since   02-14-2021
 */
public interface Problem<State, Action, Player> {

    /**
     * Return the initial state of the problem
     * 
     * @return      the intial state
     */
    public State initialState();

    /**
     * Determine whose turn it is in the given state
     * 
     * @param s     {@code State} current state
     * @return      the player whose turn it is
     */
    public Player whoseTurn(State s);   

    /**
     * Return the list of possible actions in the given state
     * 
     * @param s     {@code State} current state
     * @return      {@code ArrayList} of actions
     */
    public ArrayList<Action> actions(State s);

    /**
     * Return the next state after Action a is applied to State s
     * 
     * @param s     {@code State} current state
     * @param a     action to be applied
     * @return      successor state
     */
    public State result(State s, Action a);

    /**
     * Is the given state a terminal state?
     * 
     * @param s     {@code State} current state
     * @return      {@code true} if state is a terminal state, {@code false} otherwise.
     */
    public boolean isTerminal(State s);

    /**
     * Utility function as described in the book
     * 
     * @param s     {@code State} current state
     * @param p     current player
     * @return      {@code -1} if loss, {@code 0} if tie, {@code 1} if win.
     */
    public int utility(State s, Player p);
    
    /**
     * Heuristic function for the given problem
     * 
     * @param s     {@code State} current state
     * @return      integer value representing the heuristic value of the state
     */
    public int heuristic(State s);

}