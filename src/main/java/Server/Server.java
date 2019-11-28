package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class Server {
    ServerSocket server;
    ArrayList<Connection> connections;

    Server(int port) {
        connections = new ArrayList<>();
        try {
            server = new ServerSocket(port);
        }
        catch (IOException e) {
            System.out.println("Could not listen on port "+port); System.exit(-1);
        }
        while(true) {
            connect();
        }
    }

    void connect() {
        try {
            connections.add(new Connection(server.accept()));
        }
        catch (IOException e) {
            System.out.println("Accept failed: 4444"); System.exit(-1);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(4444);
    }
}
