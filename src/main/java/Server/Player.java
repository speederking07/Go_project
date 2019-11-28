package Server;

import Server.Exceprions.ConnectionTroubleException;
import Server.Exceprions.IllegalMoveException;

public interface Player {
    Move getMove(final Move prevMove, final Map map) throws ConnectionTroubleException;
    Move wrongMove(IllegalMoveException ex) throws ConnectionTroubleException;
    void endGame(String reason, int yoursPoints, int otherPoints);
    void startGame(Color c) throws ConnectionTroubleException;
}
