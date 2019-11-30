package Client;

import java.io.*;
import java.net.*;

public class client {
    Socket socket;
    PrintWriter outMessage;
    BufferedReader inMessage;
    String message;
    static client goClient;
    panelAction myPanelAction;
    panelBoard myPanelBoard;
    playGo frame;

    private client(panelBoard myPanelBoard, panelAction myPanelAction, playGo frame) {
        this.myPanelAction=myPanelAction;
        this.myPanelBoard=myPanelBoard;
        this.frame=frame;
        listenSocket();
    }

    public static client getInstance(panelBoard myPanelBoard, panelAction myPanelAction, playGo frame) {
        if (goClient == null)
        {
            return new client(myPanelBoard, myPanelAction, frame);
        }
        else
            return null;
    }

    private void listenSocket() {
        while(true)
        {
            try {
                socket = new Socket("localhost", 4442);
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

    public void sendToServer(String message) {
        myPanelAction.setWhichTurn("Przeciwnika");
        outMessage.println(message);
        recieveFromServer();
    }

    public void recieveFromServer() {
        try {
            message=inMessage.readLine();
            System.out.println(message);
        } catch (IOException e) {
            System.out.println("Read failed"); System.exit(1);
        }
        if(message.equals("WHITE"))
            myPanelAction.setColor("Bialy");
        if(message.equals("BLACK"))
        {
            myPanelAction.setColor("Czarny");
            myPanelAction.setWhichTurn("Twoj");
        }
        
        if(message.contains("!"))
        {
            String[] stringParts = message.split("!");
            if(stringParts[0].equals("WRONGMOVE"))
            {
                myPanelAction.setWhichTurn("Twoj");
                myPanelBoard.wrongMove(stringParts);
            }
            else if(stringParts[0].equals("ENDGAME"))
            {
                System.out.println("x");
                new endGame(frame, stringParts[1], stringParts[2], stringParts[3]);
            }
            else
            {
                myPanelAction.setWhichTurn("Twoj");
                myPanelAction.setOpponentMove(stringParts[0]);
                myPanelBoard.setStonePositions(stringParts[1].toCharArray());
            }
        }
    }
}