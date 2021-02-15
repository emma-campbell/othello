package othello.ai.search;

import java.util.ArrayList;

public interface Problem<State, Action, Player> {

    public State initialState();

    public Player whoseTurn(State s);

    public ArrayList<Action> actions(State s);

    public State result(State s, Action a);

    public boolean isTerminal(State s);

    public int utility(State s, Player p);
    
}