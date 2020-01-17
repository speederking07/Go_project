package client.socketClient;

import java.io.*;
import java.net.*;

public class Client implements ClientInterface{
    Socket socket;
    PrintWriter outMessage;
    public BufferedReader inMessage;
    static Client goClient;
    static int port;

    private Client() {
        listenSocket();
    }

    public static Client getInstance(int port) {
        if (goClient == null)
        {
            synchronized (Client.class)
            {
                if(goClient==null)
                {
                    Client.port=port;
                    goClient = new Client();
                }
            }
        }
        return goClient;
    }

    private void listenSocket() {
        while(true)
        {
            try {
                socket = new Socket("localhost", port);
                outMessage = new PrintWriter(socket.getOutputStream(), true);
                inMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                break;
            } catch (UnknownHostException e) {
                System.out.println("Unknown host: localhost");
            } catch (IOException e) {
                System.out.println("No I/O");
            }
        }
    }

    @Override
    public void sendToServer(String message) {
        outMessage.println(message);
    }

    @Override
    public String recieveFromServer() {
        String message;
        try {
            message=inMessage.readLine();
            System.out.println(message);
        } catch (IOException e) {
            System.out.println("Read failed"); System.exit(1);
            return "";
        }
        return message;
    }
}
