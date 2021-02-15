package othello.ai.algorithms;

import othello.ai.search.Problem;

public interface SearchAlgorithm<State, Action> {
    
    public Action solve(State s);
    
}
