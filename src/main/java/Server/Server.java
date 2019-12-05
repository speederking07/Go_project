package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Class responsible of connections handling
 */
class Server {
    final static public int PORT = 4444;
    ServerSocket server;

    /**
     * Basic constructor of Server
     *
     * @param port - Port to use
     */
    Server(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port);
            System.exit(-1);
        }
        while (true) {
            connect();
        }
    }

    /**
     * Accepts connections and creates a Connection object to handle communication
     */
    void connect() {
        try {
            Connection connection = new TCPConnection(server.accept());
            connection.initializeConnection();
        } catch (IOException e) {
            System.out.println("Accept failed: 4444");
            System.exit(-1);
        }
    }

    /**
     * Just a main
     *
     * @param args
     */
    public static void main(String[] args) {
        new Server(PORT);
    }
}
