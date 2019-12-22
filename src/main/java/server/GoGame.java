package server;

import org.hibernate.Session;
import server.database.DBGame;
import server.database.DBMove;
import server.database.HibernateFactory;
import server.exceprions.ConnectionTroubleException;
import server.exceprions.GiveUpException;
import server.exceprions.IllegalMoveException;
import server.exceprions.KoException;
import moves.*;
import org.javatuples.Pair;

/**
 * Class represents game state
 */
public class GoGame implements Game {
    private Color turn = Color.Black;
    private int turnCounter;
    private Player[] player;
    private Move[] moves;
    private Map prev, curr, safeCopy;
    private int[] pow; //Taken enemy stones
    private DBGame dbGame;
    private Session session;

    /**
     * Creates games
     *
     * @param p1        - black player
     * @param p2        - white player
     * @param boardSize - board size
     */
    GoGame(Player p1, Player p2, int boardSize) {
        player = new Player[2];
        player[0] = p1;
        player[1] = p2;
        pow = new int[2];
        moves = new Move[]{new Empty(), new Empty()};
        prev = new Map(boardSize);
        curr = new Map(boardSize);
        turnCounter = 0;
        session = HibernateFactory.getSessionFactory().openSession();
        session.beginTransaction();
        dbGame = new DBGame('R');
        session.save(dbGame);
    }

    /**
     * Computes game score
     *
     * @return - (BlackScore, WhiteScore)
     */
    private Pair<Integer, Integer> getScore() {
        Pair<Integer, Integer> areaRes = curr.getAreaPoints();
        return new Pair<>(areaRes.getValue0() + pow[0], areaRes.getValue1() + pow[1]);
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
                    prev.toString().equals(curr.toString())){
                throw new KoException();
            }
            prev = safeCopy;
            this.pow[turn.getIndex()] += pow;
        } else if (move instanceof GiveUp){
            throw new GiveUpException();
        }
        session.save(new DBMove(turnCounter, dbGame, move));
        moves[turn.getIndex()] = move;
    }

    /**
     * Main game loop.
     * Responsible of gating moves from players and giving them final score
     */
    @Override
    public final void run() {
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
                turnCounter++;
            }
        } catch (ConnectionTroubleException | GiveUpException ex) {
            //Unfinished game
            player[turn.getIndex()].endGame(ex.toString(), 0, 999);
            player[turn.getOpposite().getIndex()].endGame(ex.toString(), 999, 1);
            dbGame.setResult('T');
            session.update(dbGame);
            session.getTransaction().commit();
            return;
        }
        Pair<Integer, Integer> res = getScore();
        player[0].endGame("PASS", res.getValue0(), res.getValue1());
        player[1].endGame("PASS", res.getValue1(), res.getValue0());
        if(res.getValue0() > res.getValue1()) dbGame.setResult('B');
        else dbGame.setResult('W');
        session.update(dbGame);
        session.getTransaction().commit();
    }
}
