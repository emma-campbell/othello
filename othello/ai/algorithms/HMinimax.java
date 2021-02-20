package othello.ai.algorithms;

import othello.ai.search.Problem;

public class HMinimax<State, Action, Player> implements SearchAlgorithm<State, Action> {

    private Problem<State, Action, Player> prob;
    private int depthLimit;
    private int statesVisited;

    public HMinimax(Problem<State, Action, Player> problem, int lim) {
        this.prob = problem;
        this.depthLimit = lim;
    }

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

    private boolean cutoff(int depth) {
        return depth >= depthLimit;
    }
}