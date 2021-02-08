package othello.game;

import java.awt.Point;
import java.util.Scanner;

import othello.game.exceptions.IllegalBoardDimensions;
import othello.player.Player;
import othello.tui.Utils;

public class Engine {
    
    private Board board;
    
    private Player p1;
    private Player p2;

    private Color turn;

    public int p1Score;
    public int p2Score;

    public Engine(int dim, Player p1, Player p2) {
        
        try {
            this.board = new Board(dim);
        } catch (IllegalBoardDimensions e) {
            System.out.println("ERROR: " + e.toString());
        }

        this.p1 = p1;
        this.p2 = p2;

        turn = p1.color;
    }

    private Player decideWinner() {
        
        int p1Score = board.getPlayerPeices(p1.color);
        int p2Score = board.getPlayerPeices(p2.color);

        if (p1Score == p2Score) {
            return null;
        } else if (p1Score > p2Score) {
            return p1;
        } else {
            return p2;
        }
    }

    public int getScore(Color c) {
        return board.getPlayerPeices(c);
    }

    public Player play() {
        
        Scanner input = new Scanner(System.in);
        
        String p1Color = ((p1.color == Color.DARK) ? "DARK" : "LIGHT");
        String p2Color = ((p2.color == Color.DARK) ? "DARK" : "LIGHT");

        while (!(board.isGameFinished())) {
            
            Utils.clear();

            System.out.println("X = DARK, O = LIGHT\n");
            printBoard();
            
            if (board.hasAnyMoves(p1.color) || board.hasAnyMoves(p2.color)) {
                if (turn == p1.color) {
                    if (board.hasAnyMoves(p1.color)) {
                        if (p1.isUserPlayer()) {
                            
                            Point move;
                            
                            do {
                                System.out.print("\n" + p1Color + ", PLEASE CHOSE A SPACE [rowcol (i.e. a3)]: ");
                                String space = input.next();
                                
                                move = stringToPoint(space);
                                
                                if (board.validMove(p1.color, move.x, move.y)) {
                                    break;
                                }
                                
                            } while (!board.validMove(p1.color, move.x, move.y));
                            
                            board.refreshBoard(move, p1.color);
                            turn = p2.color;
                            
                        } else {
                            Point move;
                            move = p1.play(board);
                            board.refreshBoard(move, p1.color);
                            turn = p2.color;
                        }
                    } else {
                        System.out.println("\n" + p1Color + "HAS NO LEGAL MOVES.");
                        turn = p2.color;
                    }
                } else {
                    if (board.hasAnyMoves(p2.color)) {
                        if (p2.isUserPlayer()) {
                            
                            Point move;
                            
                            do {
                                System.out.print("\n" + p2Color + ", PLEASE CHOSE A SPACE [rowcol (i.e. a3)]: ");
                                String space = input.next();
                                
                                move = stringToPoint(space);
                                
                                if (board.validMove(p2.color, move.x, move.y)) {
                                    break;
                                }
                                
                            } while (!board.validMove(p2.color, move.x, move.y));
                            
                            board.refreshBoard(move, p2.color);
                            turn = p1.color;
                            
                        } else {
                            Point move = p2.play(board);
                            board.refreshBoard(move, p2.color);
                            turn = p1.color;
                        }
                    } else {
                        System.out.println("\n" + p2Color + "HAS NO LEGAL MOVES.");
                        turn = p1.color;
                    }
                }
            }
        }

        input.close();
        return decideWinner();
    }
    
    private Point stringToPoint(String input) {
        
        int row = 0;
        int col = 0;
        
        char colVal = input.charAt(0);
        char rowVal = input.charAt(1);

        char[] cols = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };

        for (int i = 0; i < board.dim; i++) {
            if (cols[i] == colVal) {
                col = i;
            }
        }

        row = Character.getNumericValue(rowVal);

        return new Point(row - 1, col);
    }

    public void printBoard() {
        System.out.print(board.toString());
    }
}
