package othello.player;

import java.awt.Point;

import othello.game.Board;
import othello.game.Color;
import othello.ai.Minimax;

public class AI extends Player {

    private int searchDepth;
    private boolean prune;

    public AI(Color c, int depth, boolean prune) {
        
        super(c);

        this.searchDepth = depth;
        this.prune = prune;
        
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
    public Point play(Board b) {
        return Minimax.solve(b, this.color, this.searchDepth, this.prune);
    }
    
}
