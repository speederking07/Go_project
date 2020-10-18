package client.socketClient;

public interface ClientInterface
{
    public void sendToServer(String message);
    public String recieveFromServer();
}