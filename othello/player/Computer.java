package othello.player;

import java.awt.Point;

import othello.ai.search.Problem;
import othello.game.Board;
import othello.game.Color;
import java.util.Random; 
import java.util.ArrayList;

public class Computer extends Player {
    
    private Random rand = new Random(); 

    public Computer (Color c){
        super(c);
    }

    @Override
    public boolean isUserPlayer() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String playerName() {
        // TODO Auto-generated method stub
        return "Computer";
    }

    @Override
    public Point play(Problem<Board, Point, Color> problem) {
        ArrayList<Point> list = problem.actions(problem.initialState());
        return list.get(rand.nextInt(list.size()));
    }
    
}