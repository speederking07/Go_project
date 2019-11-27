package Server;

import Server.Exceprions.ConnectionTroubleException;
import Server.Exceprions.IllegalMoveException;
import Server.Exceprions.KoException;

public class Game implements Runnable {
    private Color turn = Color.Black;
    private Player[] player;
    private int size;
    private Move[] moves;
    private Map prev, curr;
    private int[] POW;

    public Game(Player p1, Player p2, int boardSize) {
        size = boardSize;
        player = new Player[2];
        player[0] = p1;
        player[1] = p2;
        POW = new int[2];
        moves = new Move[2];
        prev = new Map(size);
        curr = new Map(size);
    }

    private void makeMove(Move move) throws IllegalMoveException {
        Map safeCopy = curr.clone();
        if (move instanceof PutStone) {
            PutStone p = (PutStone) move;
            int pow = curr.putStone(p.x, p.y, turn);
            if (prev.toString().equals(curr.toString())) throw new KoException();
            prev = safeCopy;
            POW[turn.getIndex()] += pow;
        }
        moves[turn.getIndex()] = move;
    }

    @Override
    public void run() {
        try {
            while (moves[0] instanceof Pass && moves[1] instanceof Pass) {
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
            player[0].endGame("ConnectionTrouble",1,0);
            player[0].endGame("ConnectionTrouble",0,1);
        }
        //TODO: Results
        player[0].endGame("2 pass",1,0);
        player[0].endGame("2 pass",0,1);
    }
}
