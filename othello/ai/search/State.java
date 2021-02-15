package othello.ai.search;

public interface State<Player> {
    
    public boolean isTerminal();

    public Player whoseTurn();
    
}
