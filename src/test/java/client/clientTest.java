package Client;

import static org.junit.Assert.assertNotSame;

import org.junit.Test;
import java.net.*;
import java.io.*;


public class clientTest
{
    @Test
    public void singletonTest() throws IOException
    {
        ServerSocket server = new ServerSocket(4442);
        client client2 = client.getInstance(null, null, null);
        client client1 = client.getInstance(null, null, null);

        assertNotSame(client1, client2);
        server.close();
    }
    @Test
    public void sendToServerTest() throws IOException
    {
        ServerSocket server = new ServerSocket(4442);
        client client2 = client.getInstance(new panelBoard(9), new panelAction(), null);
        Reader inputString = new StringReader("hi!hi");
        client2.inMessage = new BufferedReader(inputString);
        client2.recieveFromServer();
        server.close();
    }

}