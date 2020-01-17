package server;

import moves.GiveUp;
import moves.Move;
import moves.Pass;
import moves.PutStone;
import server.exceprions.ConnectionTroubleException;
import server.exceprions.IllegalMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class QueueTest {

    Player p1, p2;
    @BeforeEach
    void setUp() throws ConnectionTroubleException {
        p1 = mock(Player.class);
        when(p1.getMove(any(Move.class), any(Map.class))).thenReturn(new GiveUp());
        when(p1.wrongMove(any(IllegalMoveException.class))).thenReturn(new GiveUp());
        p2 = mock(Player.class);
        when(p2.getMove(any(Move.class), any(Map.class))).thenReturn(new PutStone(2, 2));
        when(p2.wrongMove(any(IllegalMoveException.class))).thenReturn(new Pass());
    }

    @Test
    void makeGameWithPlayer9() throws InterruptedException {
        Queue.getInstance().makeGameWithPlayer(p1, 9);
        Queue.getInstance().makeGameWithPlayer(p2, 9);
        Thread.sleep(100);
        verify(p1).endGame(anyString(), anyInt(), anyInt());
        verify(p2).endGame(anyString(), anyInt(), anyInt());
    }

    @Test
    void makeGameWithPlayer13() throws InterruptedException {
        Queue.getInstance().makeGameWithPlayer(p2, 13);
        Queue.getInstance().makeGameWithPlayer(p2, 13);
        Thread.sleep(100);
        verify(p2, times(2)).endGame(anyString(), anyInt(), anyInt());
    }

    @Test
    void makeGameWithPlayer19() throws InterruptedException {
        Queue.getInstance().makeGameWithPlayer(p1, 19);
        Queue.getInstance().makeGameWithPlayer(p2, 19);
        Thread.sleep(100);
        verify(p1).endGame(anyString(), anyInt(), anyInt());
        verify(p2).endGame(anyString(), anyInt(), anyInt());
    }

    @Test
    void makeGameWithBot9() throws InterruptedException {
        Queue.getInstance().makeGameWithBot(p1, 9);
        Thread.sleep(100);
        verify(p1).endGame(anyString(), anyInt(), anyInt());
    }

    @Test
    void makeGameWithBot13() throws InterruptedException {
        Queue.getInstance().makeGameWithBot(p1, 13);
        Thread.sleep(100);
        verify(p1).endGame(anyString(), anyInt(), anyInt());
    }

    @Test
    void makeGameWithBot19() throws InterruptedException {
        Queue.getInstance().makeGameWithBot(p1, 19);
        Thread.sleep(100);
        verify(p1).endGame(anyString(), anyInt(), anyInt());
    }
}