package Server;

import Server.Exceprions.ConnectionTroubleException;
import Server.Exceprions.GiveUpException;
import Server.Exceprions.IllegalMoveException;
import Server.Exceprions.KoException;
import org.javatuples.Pair;

public class Game implements Runnable {
    private Color turn = Color.Black;
    private Player[] player;
    private Move[] moves;
    private Map prev, curr;
    private int[] POW;

    public Game(Player p1, Player p2, int boardSize) {
        player = new Player[2];
        player[0] = p1;
        player[1] = p2;
        POW = new int[2];
        moves = new Move[2];
        prev = new Map(boardSize);
        curr = new Map(boardSize);
    }

    private Pair<Integer, Integer> getResults(){
        Pair<Integer, Integer> areaRes = curr.getAreaPoints();
        return new Pair<>(areaRes.getValue0() + POW[0], areaRes.getValue1() + POW[1]);
    }

    private void makeMove(Move move) throws IllegalMoveException, GiveUpException {
        Map safeCopy = curr.clone();
        if (move instanceof PutStone) {
            PutStone p = (PutStone) move;
            int pow = curr.putStone(p.x, p.y, turn);
            if (!(moves[turn.getOpposite().getIndex()] instanceof Pass) &&
                    prev.toString().equals(curr.toString())) throw new KoException();
            prev = safeCopy;
            POW[turn.getIndex()] += pow;
        }
        else if(move instanceof GiveUp) throw new GiveUpException();
        moves[turn.getIndex()] = move;
    }

    @Override
    public void run() {
        try {
            while (!(moves[0] instanceof Pass) && !(moves[1] instanceof Pass)) {
                IllegalMoveException exception = null;
                try {
                    makeMove(player[turn.getIndex()].getMove(moves[turn.getOpposite().getIndex()], curr));
                } catch (IllegalMoveException ex) {
                    exception = ex;
                }
                while (exception != null) {
                    try {
                        makeMove(player[turn.getIndex()].wrongMove(exception));
                    } catch (IllegalMoveException ex) {
                        exception = ex;
                    }
                }
                turn = turn.getOpposite();
            }
        }catch (ConnectionTroubleException ex)
        {
            //Unfinished game
            player[0].endGame("CONNECTION",0,0);
            player[1].endGame("CONNECTION",0,1);
            return;
        }catch (GiveUpException ex){
            if (turn == Color.Black){
                player[0].endGame("GIVEUP",0,999);
                player[1].endGame("GIVEUP",999,0);
            }
            else{
                player[0].endGame("GIVEUP",999,0);
                player[1].endGame("GIVEUP",0,999);
            }
        }
        Pair<Integer, Integer> res = getResults();
        player[0].endGame("PASS", res.getValue0(), res.getValue1());
        player[1].endGame("PASS", res.getValue1(), res.getValue0());
    }
}
