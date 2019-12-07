package client.connection;

public interface ConnectionInterface
{
    public void startConnection();
    public void startGame();
    public void sendToServer(String message);
    public String recieveFromServer();
}