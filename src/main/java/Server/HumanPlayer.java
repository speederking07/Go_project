package Server;

import Server.Exceprions.ConnectionTroubleException;
import Server.Exceprions.IllegalMoveException;

public class HumanPlayer implements Player {
    private Connection connection;
    HumanPlayer(Connection conn){
        connection = conn;
    }

    @Override
    public Move getMove(Move prevMove, Map map) throws ConnectionTroubleException {
        String ans = connection.communicate(prevMove.toString()+"!"+map.toString());
        return Move.getMove(ans);
    }

    @Override
    public Move wrongMove(IllegalMoveException ex) throws ConnectionTroubleException {
        String ans = connection.communicate("WRONGMOVE!"+ex.toString());
        return Move.getMove(ans);
    }

    @Override
    public void endGame(String reason, int yoursPoints, int otherPoints) {
        try {
            connection.say("ENDGAME!" + reason + "!" + yoursPoints + "!" + otherPoints);
        }catch (ConnectionTroubleException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void startGame(Color c) throws ConnectionTroubleException{
        connection.communicate(c.toString());
    }
}
