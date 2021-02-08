package othello.player;

import java.awt.Point;

import othello.game.Board;
import othello.game.Color;

public abstract class Player {
    
    public Color color;

    public Player(Color c) {
        this.color = c;
    }
    
    abstract public boolean isUserPlayer();

    abstract public String playerName();

    abstract public Point play(Board b);
}
