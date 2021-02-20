package othello.ai.algorithms;

import othello.ai.search.Problem;

/**
 * @author Emma Campbell & Emma Schechter
 * @since 02-20-2021
 */
public class HMinimax<State, Action, Player> implements SearchAlgorithm<State, Action> {

    private Problem<State, Action, Player> prob;        // Generic Instance of Problem
    private int depthLimit;                             // Depth Limit
    private int statesVisited;                          // Number of States visited

    /**
     * Create a new instance of the H-MINIMAX solver
     * 
     * @param problem   generic instance of {@code Problem}
     * @param lim       depth limit
     */
    public HMinimax(Problem<State, Action, Player> problem, int lim) {  
        this.prob = problem;
        this.depthLimit = lim;
    }

    /**
     * Solve for the next best state
     * 
     * @param s         current {@code State}
     */
    @Override
    public Action solve(State s) {
        
        statesVisited = 0;                              // Reset States Visited to 0

        long startTime = System.currentTimeMillis();    // Store the start time for the timer

        int max = Integer.MIN_VALUE;                    // MIN_VALUE to represent -inf
        Action action = null;                           // Store the action that we like best
        Player p = prob.whoseTurn(s);                   // Whose turn is it in this state?
        int depth = 1;                                  // Depth Level to start at

        for (Action a : prob.actions(s)) {
            int minimaxValue = minValue(prob.result(s, a), p, depth);
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
     * Return the maximum minimax value
     * 
     * @param s             current {@code State}
     * @param p             current player
     * @param depth         current depth
     * @return              maximum minimax value
     */
    private int maxValue(State s, Player p, int depth) {
        
        statesVisited++;

        if (cutoff(depth)) {
            return prob.heuristic(s);
        } else if (prob.isTerminal(s)) {
            return prob.utility(s, p);
        }

        int max = Integer.MIN_VALUE;
        depth++;

        for (Action a : prob.actions(s)) {
            max = Math.max(max, minValue(prob.result(s, a), p, depth));
        }

        return max;
    }

    /**
     * Return the minimum minimax value 
     * 
     * @param s             current {@code State}
     * @param p             current player
     * @param depth         current depth
     * @return              minimum minimax value
     */
    private int minValue(State s, Player p, int depth) {
        
        statesVisited++;

        if (cutoff(depth)) {
            return prob.heuristic(s);
        } else if (prob.isTerminal(s)) {
            return prob.utility(s, p);
        }

        int min = Integer.MAX_VALUE;
        depth++;

        for (Action a : prob.actions(s)) {
            min = Math.min(min, maxValue(prob.result(s, a), p, depth));
        }

        return min;

    }

    /**
     * Have we reached the depth limit?
     * 
     * @param depth     current depth
     * @return          {@code true} if depth >= depthLimit, {@code false} otherwise.
     */
    private boolean cutoff(int depth) {
        return depth >= depthLimit;
    }
}