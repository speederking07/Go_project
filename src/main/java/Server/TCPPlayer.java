package Server;

import Server.Exceprions.ConnectionTroubleException;
import Server.Exceprions.IllegalMoveException;
import Moves.Move;

/**
 * Object adapter of Connection to fit interface of Player
 */
public class TCPPlayer implements Player {
    private TCPConnection connection;
    TCPPlayer(TCPConnection conn){
        connection = conn;
    }

    /**
     * Get move form player instance
     * @param prevMove - move of opponent
     * @param map - map of current game state
     * @return - move to preform
     * @throws ConnectionTroubleException - connection problems
     */
    @Override
    public Move getMove(Move prevMove, Map map) throws ConnectionTroubleException {
        String ans = connection.communicate(prevMove.toString()+"!"+map.toString());
        return Move.getMove(ans);
    }

    /**
     * Confirms good move
     *
     * @param prevMove - move of opponent
     * @param map      - map of current game state
     */
    @Override
    public void goodMove(final Move prevMove, final Map map) {
        connection.say(prevMove.toString() + "!" + map.toString());
    }

    /**
     * Call in case of invalid move was get form getMove
     * @param ex - type of illegal move
     * @return - new move to preform
     * @throws ConnectionTroubleException - connection problems
     */
    @Override
    public Move wrongMove(IllegalMoveException ex) throws ConnectionTroubleException {
        String ans = connection.communicate("WRONGMOVE!"+ex.toString());
        return Move.getMove(ans);
    }

    /**
     * Call at the end of game
     * @param reason - reason of ended game
     * @param yoursPoints - yours points
     * @param otherPoints - points of opponent
     */
    @Override
    public void endGame(String reason, int yoursPoints, int otherPoints) {
        connection.say("ENDGAME!" + reason + "!" + yoursPoints + "!" + otherPoints);
    }

    /**
     * Initialization of game
     * @param c - color assigned to this player
     * @throws ConnectionTroubleException - connection problems
     */
    @Override
    public void startGame(Color c) throws ConnectionTroubleException{
        connection.communicate(c.toString());
    }
}
