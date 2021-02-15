package othello.player;

import java.awt.Point;

import othello.ai.search.Problem;
import othello.game.Board;
import othello.game.Color;

public class Human extends Player {

    public Human(Color c) {
        super(c);
    }

    @Override
    public boolean isUserPlayer() {
        return true;
    }

    @Override
    public String playerName() {
        return "USER";
    }

    @Override
    public Point play(Problem<Board, Point, Color> problem) {
        return null;
    }

}