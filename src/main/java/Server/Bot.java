package Server;

import Server.Exceprions.IllegalMoveException;
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

    public Bot(int s) {
        size = s;
    }

    private Move computeBestMove(Map currentMap) {
        int MyArea, EnemyArea;
        int bestValue = 0;
        int temp = 0;
        Move bestMove = new Pass();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Map safeCopy = currentMap.clone();
                try {
                    temp = 10 * currentMap.putStone(i, j, color);
                    Triplet<Integer, Integer, Integer> neighborhood = currentMap.neighbors(i, j, color);
                    temp += neighborhood.getValue2();
                    if(neighborhood.getValue0() == 1) temp += 2;
                    else if(neighborhood.getValue0() >= 3) temp -= 4;
                    temp +=  neighborhood.getValue1() * 2;
                } catch (IllegalMoveException ex) {
                    temp = -999;
                }
                if(temp >= bestValue){
                    bestValue = temp;
                    bestMove = new PutStone(i, j);
                }
                currentMap = safeCopy;
            }
        }
        return bestMove;
    }

    @Override
    public Move getMove(Move prevMove, Map map) {
        return computeBestMove(map);
    }

    @Override
    public Move wrongMove(IllegalMoveException ex) {
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
