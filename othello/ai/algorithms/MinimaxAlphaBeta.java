package othello.ai.algorithms;

import othello.ai.search.Problem;

/**
 * @author Emma Campbell & Emma Schechter
 * @since 02-17-2021
 */
public class MinimaxAlphaBeta<State, Action, Player> implements SearchAlgorithm<State, Action> {

    private Problem<State, Action, Player> prob;
    private int depthLimit;
    private int statesVisited;

    /**
     * Return a new instance of the H-MINIMAX with ALPHA-BETA cutoff
     * 
     * @param problem       generic instance of {@code Problem}
     * @param lim           depth limit
     */
    public MinimaxAlphaBeta(Problem<State, Action, Player> problem, int lim) {
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

        int max = Integer.MIN_VALUE;
        Action action = null;
        Player p = prob.whoseTurn(s);
        int depth = 1;

        for (Action a : prob.actions(s)) {         
            int minimaxValue = minValue(prob.result(s, a), p, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (minimaxValue > max) {
                max = minimaxValue;
                action = a;
            }
        }

        return action;
    }

    /**
     * Return the minimum minimax value 
     * 
     * @param s             current {@code State}
     * @param p             current player
     * @param depth         current depth
     * @return              minimum minimax value
     */
    private int minValue(State s, Player p, int depth, int alpha, int beta) {
        
        statesVisited++;

        if (cutoff(depth)) {
           return prob.heuristic(s);
        } else if (prob.isTerminal(s)) {
            return prob.utility(s, p);
        }

        depth++;

        int min = Integer.MAX_VALUE;

        for (Action a : prob.actions(s)) {
            
            min = Math.min(min, maxValue(prob.result(s, a), p, depth, alpha, beta));

            if (min <= alpha) {
                return min;
            }

            beta = Math.min(beta, min);
        }

        return min;
    }
    
    /**
     * Return the maximum minimax value
     * 
     * @param s             current {@code State}
     * @param p             current player
     * @param depth         current depth
     * @return              maximum minimax value
     */
    private int maxValue(State s, Player p, int depth, int alpha, int beta) {
        
        statesVisited++;

        if (cutoff(depth)) {
            return prob.heuristic(s);
        } else if (prob.isTerminal(s)) {
            return prob.utility(s, p);
        }

        depth++;

        int max = Integer.MIN_VALUE;

        for (Action a : prob.actions(s)) {
            
            max = Math.max(max, minValue(prob.result(s, a), p, depth, alpha, beta));

            if (max >= beta) {
                return max;
            }

            alpha = Math.max(alpha, max);
        }

        return max;
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
