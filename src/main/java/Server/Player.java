package Server;

import Server.Exceprions.ConnectionTroubleException;
import Server.Exceprions.IllegalMoveException;

public interface Player {
    public Move getMove(final Move prevMove, final Map map) throws ConnectionTroubleException;
    public Move wrongMove(IllegalMoveException ex) throws ConnectionTroubleException;
    public void endGame(String reason, int yoursPoints, int otherPoints);
    public void startGame(Color c) throws ConnectionTroubleException;
}
