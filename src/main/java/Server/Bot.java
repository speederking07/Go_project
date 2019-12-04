package Server;

import Server.Exceprions.IllegalMoveException;
import Server.Exceprions.KoException;
import Server.Moves.Move;
import Server.Moves.Pass;
import Server.Moves.PutStone;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Random;

//TODO: Whole bot body
public class Bot implements Player {
    Color color;
    int size;
    int[][] possibilities;
    Move prevMove;

    public Bot(int s) {
        size = s;
        possibilities = new int[size][size];
    }

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

    @Override
    public Move getMove(Move prevMove, Map map) {
        computePossibilities(map);
        prevMove = getBestMove();
        return prevMove;
    }

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

    @Override
    public void endGame(String reason, int yoursPoints, int otherPoints) {

    }

    @Override
    public void goodMove(final Move prevMove, final Map map) {

    }

    @Override
    public void startGame(Color c) {
        color = c;
    }
}
