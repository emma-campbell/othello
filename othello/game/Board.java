package othello.game;

import java.util.ArrayList;
import java.awt.Point;

import othello.ai.search.State;
import othello.game.exceptions.IllegalBoardDimensions;

public class Board implements State<Color> {
    
    private Color arr[][];
    public int dim;
    public Color turn;

    /**
     * Construct a new Board with the given dimensions
     * 
     * @param dim   the square dimension of the board
     */
    public Board(int dim, Color turn) throws IllegalBoardDimensions {

        if (!(dim == 4 || dim == 6 || dim == 8)) {
            throw new IllegalBoardDimensions();            
        }

        this.dim = dim;
        this.arr = new Color[dim][dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                arr[i][j] = Color.NONE;
            }
        }

        if (dim == 4) {
            this.arr[1][1] = Color.LIGHT;
            this.arr[1][2] = Color.DARK;
            this.arr[2][1] = Color.DARK;
            this.arr[2][2] = Color.LIGHT;
        } else if (dim == 6) {
            this.arr[2][2] = Color.LIGHT;
            this.arr[2][3] = Color.DARK;
            this.arr[3][2] = Color.DARK;
            this.arr[3][3] = Color.LIGHT;
        } else {
            this.arr[3][3] = Color.LIGHT;
            this.arr[3][4] = Color.DARK;
            this.arr[4][3] = Color.DARK;
            this.arr[4][4] = Color.LIGHT;
        }

        this.turn = turn;
    }

    /**
     * Returns the color value of the cell, accessed by parameters i and j, representing
     * the row and column indices
     * 
     * @param i     represents the row in the matrix
     * @param j     represents the col in the matrix
     * @return      Color of the given cell
     */
    public Color get(int i, int j) {
        return this.arr[i][j];
    }

    /**
     * Sets the value of the given cell to the new color c
     * 
     * @param i     represents the row on the board
     * @param j     represents the col on the board
     * @param c     represents the color of the cell
     */
    public void set(int i, int j, Color c)
    {
        this.arr[i][j] = c;
    }

    /**
     * Sets the turn to the next player
     */
    public void setTurn() {
        if (this.turn == Color.DARK) {
            this.turn = Color.LIGHT;
        } else {
            this.turn = Color.DARK;
        }
    }

    /**
     * Returns whether or not the game has ended. Terminates when neither player has any 
     * valid moves remaining.
     * 
     * @return      {@code true} if the game is over, {@code false} otherwise
     */
    public boolean isGameFinished() {
        return !(hasAnyMoves(Color.DARK) || hasAnyMoves(Color.LIGHT));
    }

    /**
     * Returns the winning color of the game.
     * 
     * @return     the {@code Color} of the winning player
     */
    public Color getWinner() {
        if (!isGameFinished()) {
            return Color.NONE;
        } else {
            
            int p1Score = getPlayerPeices(Color.DARK);
            int p2Score = getPlayerPeices(Color.LIGHT);

            if (p1Score == p2Score) {
                return Color.NONE;
            } else if (p1Score > p2Score) {
                return Color.DARK;
            } else {
                return Color.LIGHT;
            }
        }
    }

    /**
     * Gets the total number of pieces for the given player
     * 
     * @param player     Color of the player
     * @return      number of peices for the given player
     */
    public int getPlayerPeices(Color player)
    {
        int score = 0;
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (this.arr[i][j] == player) score++;
            }
        }
        return score;
    }

    /**
     * Return whether or not the given player has any moves remaining
     * 
     * @param player
     * @return
     */
    public boolean hasAnyMoves(Color player) {
        return !getAllPossibleMoves(player).isEmpty();
    }

    /**
     * Returns all possible moves for the player of the given color
     * 
     * @param player     color of the player
     * @return      ArrayList of all possible points on the board that the player could play
     */
    public ArrayList<Point> getAllPossibleMoves(Color player) {
        ArrayList<Point> res = new ArrayList<>();
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (validMove(player, i, j)) {
                    res.add(new Point(i, j));
                }
            }
        }
        return res;
    }

    /**
     * Refresh the board with the given move and player
     * 
     * @param move      {@code Point} representing the move of the player
     * @param player    the {@code Color} of the player
     */
    public void refreshBoard(Point move, Color player) {

        // Place the new piece
        set(move.x, move.y, player);

        // Reverse all the pieces
        ArrayList<Point> reverse = reversedPoints(player, move.x, move.y);

        for (Point pt: reverse) {
            set(pt.x, pt.y, player);
        }

        setTurn();
    }
    
    /**
     * Get the next board if the player goes the given move (does not save the move,
     * created for AI to work with)
     * 
     * @param move      {@code Point} representing the move of the player
     * @param player    the {@code Color} of the player
     * @return
     */
    public Board getNextBoard(Point move, Color player) {
        
        Board nextBoard;
        Color otherPlayer = (player == Color.DARK ? Color.LIGHT : Color.DARK);
        
        try {
            if (validMove(player, move.x, move.y)) {
                nextBoard = new Board(this.dim, otherPlayer);
                
                for (int i = 0; i < this.dim; i++) {
                    for (int j = 0; j < this.dim; j++) {
                        nextBoard.set(i, j, this.get(i, j));
                    }
                }
                
                nextBoard.set(move.x, move.y, player);
        
                ArrayList<Point> reverse = reversedPoints(player, move.x, move.y);
                for (Point pt : reverse) {
                    nextBoard.set(pt.x, pt.y, player);
                }
        
                return nextBoard;
            } else {
                return this;
            }
        } catch (IllegalBoardDimensions e) {
            System.out.println("ERROR: " + e.toString());
        }

        return null;
    }

    /**
     * Determines if the given move is a valid move for the given color player
     * 
     * @param player    Color of the given player
     * @param i         row on the board
     * @param j         col on the board
     * @return          {@code true} if valid, {@code false} otherwise.
     */
    public boolean validMove(Color player, int i, int j) {

        if (get(i, j) != Color.NONE) return false;

        int mi;
        int mj;
        int count;
        int limit = this.dim - 1;

        Color otherPlayer = ((player == Color.DARK) ? Color.LIGHT : Color.DARK);

        // Begin by moving up to check whether the opponent has a line of theirs moving up
        mi = i - 1;
        mj = j;
        count = 0;

        while (mi > 0 && get(mi, mj) == otherPlayer) {
            mi--;
            count++;
        }
        if (mi >= 0 && this.arr[mi][mj] == player && count > 0) return true;

        // Then, we will move down to check if ther opponent has a line moving down
        mi = i + 1;
        mj = j;
        count = 0;
        while (mi < limit && get(mi, mj) == otherPlayer) {
            mi++;
            count++;
        }
        if (mi <= limit && get(mi, mj) == player && count > 0) return true;

        // Now, the same thing moving left
        mi = i;
        mj = j - 1;
        count = 0;
        while (mj > 0 && get(mi, mj) == otherPlayer) {
            mj--;
            count++;
        }
        if (mj >= 0 && get(mi, mj) == player && count > 0) return true;

        // Let's check the right direction before checking the diagonals
        mi = i;
        mj = j + 1;
        count = 0;
        while (mj < limit && get(mi, mj) == otherPlayer) {
            mj++;
            count++;
        }
        if (mj <= limit && get(mi, mj) == player && count > 0) return true;

        // Now, we can move diagonally up towards the upper left
        mi = i - 1;
        mj = j - 1;
        count = 0;
        while (mi > 0 && mj > 0 && get(mi, mj) == otherPlayer) {
            mi--;
            mj--;
            count++;
        }
        if (mi >= 0 && mj >= 0 && get(mi, mj) == player && count > 0) return true;

        // Move diagonally towards the upper right
        mi = i - 1;
        mj = j + 1;
        count = 0;
        while(mi > 0 && mj < limit && get(mi, mj) == otherPlayer){
            mi--;
            mj++;
            count++;
        }
        if (mi >= 0 && mj <= limit && get(mi, mj) == player && count > 0) return true;

        // Move diagonally towards the lower left
        mi = i + 1;
        mj = j - 1;
        count = 0;
        while(mi < limit && mj > 0 && get(mi, mj) == otherPlayer){
            mi++;
            mj--;
            count++;
        }
        if (mi <= limit && mj >= 0 && get(mi, mj) == player && count > 0) return true;

        // Move diagonally towards the lower right
        mi = i + 1;
        mj = j + 1;
        count = 0;
        while(mi < limit && mj < limit && get(mi, mj) == otherPlayer){
            mi++;
            mj++;
            count++;
        }
        if (mi <= limit && mj <= limit && get(mi, mj) == player && count > 0) return true;

        // If none of these conditions are true, this is not a valid move.
        return false;
    }

    public ArrayList<Point> reversedPoints(Color player, int i, int j) {
        
        ArrayList<Point> allReversedPoints = new ArrayList<>();

        int mi, mj;
        int limit = this.dim - 1;
        Color otherPlayer = ((player == Color.DARK) ? Color.LIGHT : Color.DARK);

        // Like the method validMove(), we need to check for points to reversed
        // in the directions up, down, left, right, upper right, upper left,
        // lower right, lower left since the board works horizontally, vertically,
        // and diagonally.
        //
        // Let's begin by checking moving up.
        ArrayList<Point> points = new ArrayList<>();
        mi = i - 1;
        mj = j;
        while (mi > 0 && get(mi, mj) == otherPlayer) {
            points.add(new Point(mi, mj));
            mi--;
        }
        if (mi >= 0 && get(mi, mj) == player && points.size() > 0) {
            allReversedPoints.addAll(points);
        }

        // Now, move the opposite direction and go down
        points.clear();
        mi = i + 1;
        mj = j;
        while (mi < limit && get(mi, mj) == otherPlayer) {
            points.add(new Point(mi, mj));
            mi++;
        }
        if (mi <= limit && get(mi, mj) == player && points.size() > 0) {
            allReversedPoints.addAll(points);
        }

        // Now we change to the horizontal direction and start by moving left
        points.clear();
        mi = i;
        mj = j - 1;
        while (mj > 0 && get(mi, mj) == otherPlayer) {
            points.add(new Point(mi, mj));
            mj--;
        }
        if (mj >= 0 && get(mi, mj) == player && points.size() > 0) {
            allReversedPoints.addAll(points);
        }

        // Let's go to the right
        points.clear();
        mi = i;
        mj = j + 1;
        while (mj < limit && get(mi, mj) == otherPlayer) {
            points.add(new Point(mi, mj));
            mj++;
        }
        if ( mj <= limit && get(mi, mj) == player && points.size() > 0) {
            allReversedPoints.addAll(points);
        }

        // Now, we can move diagonally. We can begin by going towards the upper left
        points.clear();
        mi = i - 1;
        mj = j - 1;
        while (mi > 0 && mj > 0 && get(mi, mj) == otherPlayer) {
            points.add(new Point(mi, mj));
            mi--;
            mj--;
        }
        if (mi >= 0 && mj >= 0 && get(mi, mj) == player && points.size() > 0) {
            allReversedPoints.addAll(points);
        }

        // Now towards the upper right
        points.clear();
        mi = i - 1;
        mj = j + 1;
        while (mi > 0 && mj < limit && get(mi, mj) == otherPlayer) {
            points.add(new Point(mi, mj));
            mi--;
            mj++;
        }
        if (mi >= 0 && mj <= limit && get(mi, mj) == player && points.size() > 0) {
            allReversedPoints.addAll(points);
        }

        // Lower Left
        points.clear();
        mi = i + 1;
        mj = j - 1;
        while (mi < limit && mj > 0 && get(mi, mj) == otherPlayer) {
            points.add(new Point(mi, mj));
            mi++;
            mj--;
        }
        if (mi <= limit && mj >= 0 && get(mi, mj) == player && points.size() > 0) {
            allReversedPoints.addAll(points);
        }

        // Finally, lower right
        points.clear();
        mi = i + 1;
        mj = j + 1;
        while (mi < limit && mj < limit && get(mi, mj) == otherPlayer) {
            points.add(new Point(mi, mj));
            mi++;
            mj++;
        }
        if (mi <= limit && mj <= limit && get(mi, mj) == player && points.size() > 0) {
            allReversedPoints.addAll(points);
        }

        return allReversedPoints;
    }

    /**
     * Converts the board into string representation
     * 
     * @return     string representation of the board
     */
    public String toString() {
        
        StringBuilder res = new StringBuilder();
        
        String xaxis;
        String divide;

        if (this.dim == 4) {
            xaxis = "        a     b     c     d\n";
        } else if (this.dim == 6) {
            xaxis = "        a     b     c     d     e     f\n";
        } else {
            xaxis = "        a     b     c     d     e     f     g     h\n";
        }

        res.append(xaxis);

        for (int i = 0; i < this.dim; i++) {
            res.append((i+1) + "    | ");
            for (int j = 0; j < this.dim; j++) {
                Color c = this.get(i, j);
                if (c != Color.NONE) {
                    if (c == Color.DARK) {
                        res.append(" X  | ");
                    } else {
                        res.append(" O  | ");
                    }
                } else {
                    res.append("    | ");
                }   
            }
            res.append("  " + (i+1));

            if (i != this.dim - 1) {
                
                if (this.dim == 4) {
                    divide = "\n     -------------------------\n";
                } else if (this.dim == 6) {
                    divide = "\n     -------------------------------------\n";
                } else {
                    divide = "\n     -------------------------------------------------\n";
                }

                res.append(divide);
            }
        }

        res.append("\n" + xaxis);

        return res.toString();
    }

    @Override
    public boolean isTerminal() {
        return isGameFinished();
    }

    @Override
    public Color whoseTurn() {
        return this.turn;
    }

}
