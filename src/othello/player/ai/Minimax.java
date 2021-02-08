package othello.player.ai;

import java.awt.Point;

import othello.game.Board;
import othello.game.Color;

public class Minimax {
    
    static int nodesExplored = 0;

    public static Point solve(Board b, Color player, int depth, boolean prune) {
        nodesExplored = 0;

        int best = Integer.MIN_VALUE;

        Point bestMove = null;

        for (Point move : b.getAllPossibleMoves(player)) {
            
            Board nextBoard = b.getNextBoard(move, player);

            int childScore;

            if (prune) {
                childScore = minimax_ab(nextBoard, player, depth-1, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            } else {
                childScore = minimax(nextBoard, player, depth-1, false);
            }

            if (childScore > best) {
                best = childScore;
                bestMove = move;
            }
        }

        return bestMove;
    }

    public static int minimax(Board b, Color player, int depth, boolean max) {

        // Leaf Case
        if (depth == 0 || b.isGameFinished()) {
            return Heuristics.eval(b, player);
        }

        Color otherPlayer = ((player == Color.DARK) ? Color.LIGHT : Color.DARK);

        if ((max && b.hasAnyMoves(player)) || (!max && !b.hasAnyMoves(player))) {
            return minimax(b, player, depth-1, !max);
        }

        int score;

        if (max) {
            score = Integer.MIN_VALUE;

            for (Point move : b.getAllPossibleMoves(player)) {
                Board nextBoard = b.getNextBoard(move, player);

                int childScore = minimax(nextBoard, player, depth-1, false);
                if (childScore > score) score = childScore;
            }
        } else {
            score = Integer.MAX_VALUE;

            for (Point move : b.getAllPossibleMoves(otherPlayer)) {
                Board nextBoard = b.getNextBoard(move, otherPlayer);

                int childScore = minimax(nextBoard, player, depth-1, true);
                if (childScore < score) score = childScore;
            }
        }

        return score;
    }

    public static int minimax_ab(Board b, Color player, int depth, boolean max, int alpha, int beta) {
        
        nodesExplored++;

        if (depth == 0 || b.isGameFinished()) {
            return Heuristics.eval(b, player);
        }

        Color otherPlayer = ((player == Color.DARK) ? Color.LIGHT : Color.DARK);

        if ((max && !b.hasAnyMoves(player)) || (!max && !b.hasAnyMoves(otherPlayer))) {
            return minimax_ab(b, player, depth-1, !max, alpha, beta);
        }

        int score;

        if (max) {

            score = Integer.MIN_VALUE;

            for (Point move : b.getAllPossibleMoves(player)) {
                
                Board nextBoard = b.getNextBoard(move, player);
                
                int childScore = minimax_ab(nextBoard, player, depth-1, false, alpha, beta);
                if (childScore > score) score = childScore;

                // A-B Pruning -- Beta Cutoff
                if (score > alpha) alpha = score;
                if (beta <= alpha) break;
            }
        } else {
            score = Integer.MAX_VALUE;

            for (Point move : b.getAllPossibleMoves(otherPlayer)) {
                
                Board nextBoard = b.getNextBoard(move, otherPlayer);

                int childScore = minimax_ab(nextBoard, player, depth-1, true, alpha, beta);
                if (childScore < score) score = childScore;

                // A-B Pruning -- Alpha Cutoff
                if (score < beta) beta = score;
                if (beta <= alpha) break;
            }
        }

        return score;
    }
}
