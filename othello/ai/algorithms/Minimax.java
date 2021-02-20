package othello.ai.algorithms;

import othello.ai.search.Problem;

/**
 * @author  Emma Campbell & Emma Schechter
 * @since   02-14-2021
 */
public class Minimax<State, Action, Player> implements SearchAlgorithm<State, Action> {

    private Problem<State, Action, Player> prob;
    private int statesVisited;

    /**
     * Create a new instance of the MINIMAX solver
     * 
     * @param problem       generic instance of {@code Problem}
     */
    public Minimax(Problem<State, Action, Player> problem){
        this.prob = problem;
    }

    /**
     * Generic algorithm in which a state is supplied, and the minimax 
     * algorithm is implemented. 
     * 
     * @param s     state the game is currently in
     * @return      next desirable action 
     */
    @Override
    public Action solve(State s) {
        statesVisited = 0;
        int max = Integer.MIN_VALUE;
        Action action = null;
        Player p = this.prob.whoseTurn(s);

        for (Action a : this.prob.actions(s)) {
            int minimaxValue = minValue(this.prob.result(s, a), p);
            if (minimaxValue > max) {
                max = minimaxValue;
                action = a;
            }
        }

        return action;
    }

    /**
     * Returns the maximum value backed up the tree for the given state
     * 
     * @param s     {@code State} given state
     * @param p     current turn
     * @return      max value of the subtree
     */
    private int maxValue(State s, Player p) {
		
        statesVisited++;

		if (prob.isTerminal(s)) {
			return prob.utility(s, p);
		} 
		
		int max = Integer.MIN_VALUE;

		for (Action a : prob.actions(s)) {
			max = Math.max(max, minValue(prob.result(s, a), p));
		}

		return max;
	
	}

    /**
     * Return the minimum value backed up the tree for the given state
     * 
     * @param s     {@code State} given state
     * @param p     current turn
     * @return      min value of the subtree
     */
	private int minValue(State s, Player p) {
		
        statesVisited++;

		if (prob.isTerminal(s)) {
			return prob.utility(s, p);
		} 
		
		int min = Integer.MAX_VALUE;

		for (Action a : prob.actions(s)) {
			min = Math.min(min, maxValue(prob.result(s, a), p));
		}

		return min;
	}


}
