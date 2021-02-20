package othello.player;

import java.awt.Point;

import othello.ai.search.Problem;
import othello.game.Board;
import othello.game.Color;

/**
 * @author  Emma Campbell & Emma Schechter
 * @since   02-09-2021
 */
public class Human extends Player {

    public Human(Color c) {
        super(c);
    }

    @Override
    public boolean isUserPlayer() {
        return true;
    }

    @Override
    public Point play(Problem<Board, Point, Color> problem) {
        return null;
    }

}