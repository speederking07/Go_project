package Server;

import Server.Exceprions.ConnectionTroubleException;
import Server.Exceprions.GiveUpException;
import Server.Exceprions.IllegalMoveException;
import Server.Exceprions.KoException;
import Server.Moves.*;
import org.javatuples.Pair;

/**
 * Class represents game state
 */
public class Game implements Runnable {
    private Color turn = Color.Black;
    private Player[] player;
    private Move[] moves;
    private Map prev, curr, safeCopy;
    private int[] POW; //Taken enemy stones

    /**
     * Creates games
     *
     * @param p1        - black player
     * @param p2        - white player
     * @param boardSize - board size
     */
    public Game(Player p1, Player p2, int boardSize) {
        player = new Player[2];
        player[0] = p1;
        player[1] = p2;
        POW = new int[2];
        moves = new Move[]{new Empty(), new Empty()};
        prev = new Map(boardSize);
        curr = new Map(boardSize);
    }

    /**
     * Computes game score
     *
     * @return - (BlackScore, WhiteScore)
     */
    private Pair<Integer, Integer> getScore() {
        Pair<Integer, Integer> areaRes = curr.getAreaPoints();
        return new Pair<>(areaRes.getValue0() + POW[0], areaRes.getValue1() + POW[1]);
    }

    /**
     * Performed move
     *
     * @param move - move to preform
     * @throws IllegalMoveException - in case if move was invalid
     * @throws GiveUpException      - in case if move was instance of GiveUp
     */
    private void makeMove(Move move) throws IllegalMoveException, GiveUpException {
        safeCopy = curr.clone();
        if (move instanceof PutStone) {
            PutStone p = (PutStone) move;
            int pow = curr.putStone(p.getX(), p.getY(), turn);
            if (!(moves[turn.getOpposite().getIndex()] instanceof Pass) &&
                    prev.toString().equals(curr.toString())) throw new KoException();
            prev = safeCopy;
            POW[turn.getIndex()] += pow;
        } else if (move instanceof GiveUp) throw new GiveUpException();
        moves[turn.getIndex()] = move;
    }

    /**
     * Main game loop.
     * Responsible of gating moves from players and giving them final score
     */
    @Override
    public void run() {
        try {
            while (!(moves[0] instanceof Pass) || !(moves[1] instanceof Pass)) { //Till two player won't pass
                IllegalMoveException exception = null;
                try {
                    makeMove(player[turn.getIndex()].getMove(moves[turn.getOpposite().getIndex()], curr.clone()));
                } catch (IllegalMoveException ex) {
                    exception = ex;
                }
                while (exception != null) { //Till wont get invalid move
                    try {
                        curr = safeCopy;
                        makeMove(player[turn.getIndex()].wrongMove(exception));
                        exception = null;
                    } catch (IllegalMoveException ex) {
                        exception = ex;
                    }
                }
                player[turn.getIndex()].goodMove(moves[turn.getOpposite().getIndex()], curr.clone());
                turn = turn.getOpposite();
            }
        } catch (ConnectionTroubleException ex) {
            //Unfinished game
            player[0].endGame("CONNECTION", 0, 0);
            player[1].endGame("CONNECTION", 0, 1);
            return;
        } catch (GiveUpException ex) {
            if (turn == Color.Black) {
                Pair<Integer, Integer> res = getScore();
                player[0].endGame("GIVEUP", res.getValue0(), res.getValue1());
                player[1].endGame("GIVEUP", res.getValue1(), res.getValue0());
                //player[0].endGame("GIVEUP",0,999);
                //player[1].endGame("GIVEUP",999,0);
            } else {
                player[0].endGame("GIVEUP", 999, 0);
                player[1].endGame("GIVEUP", 0, 999);
            }
        }
        Pair<Integer, Integer> res = getScore();
        player[0].endGame("PASS", res.getValue0(), res.getValue1());
        player[1].endGame("PASS", res.getValue1(), res.getValue0());
    }
}
