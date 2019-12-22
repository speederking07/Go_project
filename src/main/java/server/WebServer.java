package server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class WebServer extends WebSocketServer {
    private java.util.Map<WebSocket, WebConnection> connections;

    public WebServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println(conn + " CONNECTED");
        WebConnection web = new WebConnection(conn);
        connections.put(conn, web);
        web.initializeConnection();
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Connection lost");
        connections.get(conn).kill();
        connections.remove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        connections.get(conn).addToQueue(message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        connections = new HashMap<WebSocket, WebConnection>();
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }

}