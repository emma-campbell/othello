package othello.player;

import java.awt.Point;

import othello.ai.algorithms.HMinimax;
import othello.ai.algorithms.Minimax;
import othello.ai.algorithms.MinimaxAlphaBeta;
import othello.ai.search.Problem;
import othello.game.Board;
import othello.game.Color;

/**
 * @author  Emma Campbell & Emma Schechter
 * @since   02-14-2021
 */
public class AI extends Player {
    
    private int algo;
    private int depthLimit;

    public AI(Color c, int algo, int lim) {
        super(c);
        this.algo = algo;
        this.depthLimit = lim;
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public Point play(Problem<Board, Point, Color> problem) {

        if (algo == 2) {
            Minimax<Board, Point, Color> solver = new Minimax<>(problem);
            return solver.solve(problem.initialState());
        } 

        if (algo == 3) {
            HMinimax<Board, Point, Color> solver = new HMinimax<>(problem, depthLimit);
            return solver.solve(problem.initialState());
        }

        if (algo == 4) {
            MinimaxAlphaBeta<Board, Point, Color> solver = new MinimaxAlphaBeta<>(problem, depthLimit);
            return solver.solve(problem.initialState());
        }
        
        return null;
    }

}
