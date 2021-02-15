package othello.player;

import java.awt.Point;

import othello.ai.search.Problem;
import othello.game.Board;
import othello.game.Color;

public abstract class Player {
    
    public Color color;

    protected Player(Color c) {
        this.color = c;
    }
    
    abstract public boolean isUserPlayer();

    abstract public String playerName();

    abstract public Point play(Problem<Board, Point, Color> problem);
}