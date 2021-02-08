package othello.game.exceptions;

public class IllegalBoardDimensions extends Exception {

    private static final long serialVersionUID = 1L;

    public IllegalBoardDimensions() {
        super("Illegal Board Dimensions -- Must be 4, 6, or 8");
    }
}
