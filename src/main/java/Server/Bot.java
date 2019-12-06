package Server;

import Moves.Move;
import Moves.Pass;
import Moves.PutStone;
import Server.Exceprions.IllegalMoveException;
import Server.Exceprions.KoException;
import org.javatuples.Triplet;

public class Bot implements Player {
    Color color;
    int size;
    int[][] possibilities; //Values of possible moves
    Move prevMove;

    /**
     * Constructor of Bot
     *
     * @param s - size of board
     */
    public Bot(int s) {
        size = s;
        possibilities = new int[size][size];
    }

    /**
     * Used to compute values of possible moves
     *
     * @param currentMap - map
     */
    private void computePossibilities(Map currentMap) {
        //int MyArea, EnemyArea;
        int temp = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Map safeCopy = currentMap.clone();
                try {
                    temp = 10 * currentMap.putStone(i, j, color);
                    Triplet<Integer, Integer, Integer> neighborhood = currentMap.neighbors(i, j, color);
                    temp += neighborhood.getValue2();
                    if (neighborhood.getValue0() == 1) temp += 4;
                    else if (neighborhood.getValue0() >= 3) temp -= 4;
                    temp += neighborhood.getValue1();
                } catch (IllegalMoveException ex) {
                    temp = -999;
                }
                possibilities[i][j] = temp;
                currentMap = safeCopy;
            }
        }
    }

    /**
     * Chooses best move from array of possible moves
     *
     * @return - best move
     */
    private Move getBestMove() {
        int bestValue = 0;
        Move bestMove = new Pass();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (possibilities[i][j] >= bestValue) {
                    bestValue = possibilities[i][j];
                    bestMove = new PutStone(i, j);
                }
            }
        }
        return bestMove;
    }

    /**
     * Get move form bot
     *
     * @param enemyMove - move of opponent
     * @param map       - map of current game state
     * @return - move to preform
     */
    @Override
    public Move getMove(final Move enemyMove, Map map) {
        computePossibilities(map);
        prevMove = getBestMove();
        return prevMove;
    }

    /**
     * Call in case of invalid move was get from getMove
     *
     * @param ex - type of illegal move
     * @return - new move to preform
     */
    @Override
    public Move wrongMove(IllegalMoveException ex) {
        if (ex instanceof KoException) {
            PutStone m = (PutStone) prevMove;
            possibilities[m.getX()][m.getY()] = -999;
            prevMove = getBestMove();
            return prevMove;
        }
        return new Pass();
    }

    /**
     * Ends game
     *
     * @param reason      - reason of ended game
     * @param yoursPoints - yours points
     * @param otherPoints - points of opponent
     */
    @Override
    public void endGame(String reason, int yoursPoints, int otherPoints) {
    }

    /**
     * Confirm good move
     *
     * @param prevMove - move of opponent
     * @param map      - map of current game state
     */
    @Override
    public void goodMove(final Move prevMove, final Map map) {

    }

    /**
     * Sets color of bot
     *
     * @param c - color assigned to this player
     */
    @Override
    public void startGame(Color c) {
        color = c;
    }
}
