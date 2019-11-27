package Server;

import Server.Exeprions.IllegalMoveException;

public interface Player {
    public Move getMove();
    public Move wrongMove(IllegalMoveException ex);
    public void endGame(int yoursPoints, int otherPoints);
}
