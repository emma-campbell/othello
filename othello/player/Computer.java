package othello.player;

import java.awt.Point;

import othello.ai.search.Problem;
import othello.game.Board;
import othello.game.Color;
import java.util.Random; 
import java.util.ArrayList;

/**
 * @author  Emma Campbell & Emma Schechter
 * @since   02-09-2021
 */
public class Computer extends Player {
    
    private Random rand = new Random(); 

    public Computer (Color c){
        super(c);
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public Point play(Problem<Board, Point, Color> problem) {
        ArrayList<Point> list = problem.actions(problem.initialState());
        return list.get(rand.nextInt(list.size()));
    }
    
}