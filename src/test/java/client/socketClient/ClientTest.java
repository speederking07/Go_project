package client.socketClient;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import client.socketClient.Client;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class ClientTest
{
    static ServerSocket server;
    public static void prepare() throws IOException
    {
        if(server==null)
            server = new ServerSocket(4442);
    }
    @Test
    public void singletonTest() throws IOException, InterruptedException
    {
        prepare();
        List<Thread> threads = new ArrayList<Thread>();
        Client[] clients = new Client[10];
        for(int i=0; i<10; i++)
        {
            Thread thread = new Thread(new singletonThread(clients,i));
            threads.add(thread);
            thread.start(); //run
        }

        for (Thread thread : threads) {
			while (thread.isAlive()) {
				// just wait it to die
            }
        }
        for(int i=1; i<10; i++)
            assertSame(clients[0], clients[i]);
    }
    private class singletonThread implements Runnable
    {
        Client[] clients;
        int i;
        public singletonThread(Client[] clients, int i)
        {
            this.i=i;
            this.clients=clients;
        }
        @Override
        public void run() {
            clients[i]=Client.getInstance(4442);
        }
        
    }
    @Test
    public void communicationTest() throws IOException
    {
        prepare();
        Client client2 = Client.getInstance(4442);
        Socket socket = server.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        client2.sendToServer("message");
        String message;
        message = in.readLine();
        assertEquals("message", message);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("message");
        message = client2.recieveFromServer();
        assertEquals("message", message);
        server.close();
    }

}
