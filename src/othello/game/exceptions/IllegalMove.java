package othello.game.exceptions;

public class IllegalMove extends Exception {

    private static final long serialVersionUID = 1L;
    
    public IllegalMove() {
        super("Invalid Move");
    }
}
