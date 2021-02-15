package othello.player;

import java.awt.Point;

import othello.ai.algorithms.Minimax;
import othello.ai.algorithms.SearchAlgorithm;
import othello.ai.search.Problem;
import othello.game.Board;
import othello.game.Color;

public class AI extends Player {
    
    private int algo;

    public AI(Color c, int algo) {
        super(c);
        this.algo = algo;
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public String playerName() {
        return "AI";
    }

    @Override
    public Point play(Problem<Board, Point, Color> problem) {

        if (algo == 2) {
            Minimax<Board, Point, Color> solver = new Minimax<>(problem);
            return solver.solve(problem.initialState());
        } 

        // TODO: Minimax w/ Alpha-Beta Pruning
        // TODO: H-Minimax w/ Alpha-Beta Pruning
        
        return null;
    }

}
