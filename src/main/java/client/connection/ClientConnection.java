package client.connection;

import client.socketClient.*;
import moves.*;
import client.gui.*;

public class ClientConnection implements Runnable, ConnectionInterface
{
    ClientInterface myClient;
    GuiInterface gui;
    public ClientConnection(GuiInterface gui)
    {
        this.gui=gui;
        startConnection();
    }
    
    @Override
    public void startConnection()
    {
        myClient = Client.getInstance();
        sendToServer(gui.getStartMessage());
        recieveFromServer();
    }

    @Override
    public void sendToServer(String message)
    {
        myClient.sendToServer(message);
    }

    @Override
    public String recieveFromServer()
    {
        return myClient.recieveFromServer();
    }

    @Override
    public void startGame()
    {
        sendToServer("WAITING");
        String message = recieveFromServer();
        if(message.equals("WHITE"))
        {
            gui.changeTurn();
            gui.setColor("Bialy");
        }
        if(message.equals("BLACK"))
        {
            gui.setColor("Czarny");
            gui.changeTurn();
        }
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run()
    {
        while(true)
        {
            waitForAnswer();
        }
    }

    @Override
    public void waitForAnswer()
    {
        String message = recieveFromServer();
            if(message.contains("!"))
            {
                String[] stringParts = message.split("!");
                if(stringParts[0].equals("WRONGMOVE"))
                {
                    gui.wrongMove(stringParts[1]);
                }
                else if(stringParts[0].equals("ENDGAME"))
                {
                    gui.endGame(stringParts[1], stringParts[2], stringParts[3]);
                }
                else 
                {
                    gui.changeTurn();
                    gui.setOpponentMove(Move.getMove(stringParts[0]));
                    gui.setStonePositions(stringParts[1].toCharArray());
                }
            }
    }

}