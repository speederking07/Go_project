package client.connection;

import org.junit.jupiter.api.Test;

import client.gui.PlayGo;
import client.socketClient.Client;
//import moves.Move;

import static org.mockito.Mockito.*;

public class ClientConnectionTest {
    @Test
    public void ClientConnectionMethodsTest()
    {
        PlayGo frame = mock(PlayGo.class);
        ClientConnection connection = mock(ClientConnection.class);
        connection.myClient = mock(Client.class);
        connection.gui=frame;
        doNothing().when(connection.myClient).sendToServer(anyString());
        doCallRealMethod().when(connection).recieveFromServer();
        when(connection.myClient.recieveFromServer()).thenReturn("WHITE");

        connection.recieveFromServer();
        verify(connection.myClient).recieveFromServer();
        doCallRealMethod().when(connection).sendToServer(anyString());
        connection.sendToServer("message");
        verify(connection.myClient).sendToServer(anyString());

        doCallRealMethod().when(connection).startGame();
        connection.startGame();

        verify(connection.gui, times(1)).changeTurn();
        verify(connection.gui, times(1)).setColor("Bialy");
        
        when(connection.myClient.recieveFromServer()).thenReturn("WRONGMOVE!reason");
        doCallRealMethod().when(connection).waitForAnswer();
        connection.waitForAnswer();

        verify(connection.gui, times(1)).wrongMove("reason");

        when(connection.myClient.recieveFromServer()).thenReturn("ENDGAME!reason!score1!score2");
        connection.waitForAnswer();

        verify(connection.gui, times(1)).endGame("reason", "score1", "score2");

        when(connection.myClient.recieveFromServer()).thenReturn("PUTSTONE 1 1!BEWEWBEWB");
        connection.waitForAnswer();

        verify(connection.gui, times(2)).changeTurn();
        //verify(connection.gui, times(1)).setOpponentMove(Move.getMove("PUTSTONE 1 1"));
        verify(connection.gui, times(1)).setStonePositions("BEWEWBEWB".toCharArray());
    }
}