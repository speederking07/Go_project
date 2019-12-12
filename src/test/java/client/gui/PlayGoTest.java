package client.gui;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import client.connection.ClientConnection;
import moves.Move;

public class PlayGoTest
{
    @Test
    public void playGoMethodsTest()
    {
        PlayGo frame = mock(PlayGo.class);
        frame.connection = mock(ClientConnection.class);
        frame.myPanelAction = mock(PanelAction.class);
        frame.myPanelBoard = mock(PanelBoard.class);

        doCallRealMethod().when(frame).wrongMove(anyString());
        frame.wrongMove("reason");
        verify(frame.myPanelBoard).wrongMove("reason");

        doCallRealMethod().when(frame).changeTurn();
        frame.changeTurn();
        verify(frame.myPanelBoard, times(1)).changeTurn();
        verify(frame.myPanelAction, times(1)).changeTurn();
        
        doCallRealMethod().when(frame).setColor(anyString());
        frame.setColor("Czarny");
        verify(frame.myPanelAction, times(1)).setColor("Czarny");

        doCallRealMethod().when(frame).setOpponent(anyString());
        frame.setOpponent("opponent");
        verify(frame.myPanelAction, times(1)).setOpponent("opponent");

        doCallRealMethod().when(frame).setOpponentMove(any());
        frame.setOpponentMove(Move.getMove("EMPTY"));
        verify(frame.myPanelAction, times(1)).setOpponentMove("-");

        doCallRealMethod().when(frame).setStonePositions(any());
        frame.setStonePositions("WEBWBEWEWBEWB".toCharArray());
        verify(frame.myPanelBoard, times(1)).setStonePositions("WEBWBEWEWBEWB".toCharArray());
        
    }

    @Test
    public void observerTest()
    {
        PlayGo frame = mock(PlayGo.class);
        doNothing().when(frame).reactOnEvent("message");
        Observable observable = new Observable();
        observable.addObserver(frame);
        observable.notifyObserver("message");

        verify(frame, times(1)).reactOnEvent("message");
    }

    private class Observable implements ObservableInterface
    {
        ObserverInterface observer;

        @Override
        public void addObserver(ObserverInterface gui) {
            observer = gui;

        }

        @Override
        public void removeObserver(ObserverInterface gui) {
            observer=null;

        }

        @Override
        public void notifyObserver(String message) {
            observer.reactOnEvent(message);

        }
        
    }
}