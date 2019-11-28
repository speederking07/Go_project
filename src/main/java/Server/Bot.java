package Server;

import Server.Exceprions.ConnectionTroubleException;
import Server.Exceprions.IllegalMoveException;

import java.util.Random;

public class Bot implements Player {
    Color color;
    Map currentMap;
    int size;

    public Bot(int s){
        size = s;
    }

    @Override
    public Move getMove(Move prevMove, Map map) {
        currentMap = map.clone();
        Random rand = new Random();
        return new PutStone(rand.nextInt(size), rand.nextInt(size));
    }

    @Override
    public Move wrongMove(IllegalMoveException ex) {
        Random rand = new Random();
        return new PutStone(rand.nextInt(size), rand.nextInt(size));
    }

    @Override
    public void endGame(String reason, int yoursPoints, int otherPoints) {

    }

    @Override
    public void startGame(Color c) {
        color = c;
    }
}
