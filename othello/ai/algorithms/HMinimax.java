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
        
        statesVisited = 0;

        Action action = null;
        Player p = prob.whoseTurn(s);
        int max = Integer.MIN_VALUE;
        int depth = 1;

        for (Action a : prob.actions(s)) {
            int minimaxValue = minValue(prob.result(s, a), p, depth);
            if (minimaxValue > max) {
                max = minimaxValue;
                action = a;
            }
        }

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