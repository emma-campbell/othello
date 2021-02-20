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
        
        statesVisited = 0;                              // Reset States Visited to 0

        long startTime = System.currentTimeMillis();    // Store the start time for the timer

        int max = Integer.MIN_VALUE;                    // MIN_VALUE to represent -inf
        Action action = null;                           // Store the action that we like best
        Player p = prob.whoseTurn(s);                   // Whose turn is it in this state?

        for (Action a : prob.actions(s)) {
            int minimaxValue = minValue(prob.result(s, a), p);
            if (minimaxValue > max) {
                max = minimaxValue;
                action = a;
            }
        }

        long finishTime = System.currentTimeMillis();    // Store the end time for the timer
        float time = (finishTime - startTime) / 1000F;   // Calculate the Elapsed time

        System.out.println("Visited " + statesVisited + " states.");
        System.out.println("Elasped Time: " + time + " s.");

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
