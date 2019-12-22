package server;

import server.exceprions.ConnectionTroubleException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class responsible to handling single connection to client
 */
public class TCPConnection implements Runnable, Connection {
    private static final String PING_STRING = "PING";
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    /**
     * Constructor of connection
     *
     * @param s - socket used to connect
     * @throws IOException - in case to connection troubles
     */
    TCPConnection(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

    }

    /**
     * Initialize connection and add game to queue
     */
    @Override
    public void initializeConnection() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Sends and receives message from client
     *
     * @param msg - message to sent
     * @return - received message
     * @throws ConnectionTroubleException - in case to connection troubles
     */
    @Override
    final public String communicate(String msg) throws ConnectionTroubleException {
        out.println(msg);
        System.out.println("Sent: " + msg);
        try {
            String s = in.readLine();
            System.out.println("Received: " + s);
            return s;

        } catch (IOException e) {
            System.out.println("Read failed");
            throw new ConnectionTroubleException();
        }
    }

    /**
     * Receives message from client
     *
     * @return - received message
     * @throws ConnectionTroubleException - in case to connection troubles
     */
    @Override
    final public String listen() throws ConnectionTroubleException {
        try {
            String s = in.readLine();
            System.out.println("Received: " + s);
            return s;

        } catch (IOException e) {
            System.out.println("Read failed");
            throw new ConnectionTroubleException();
        }
    }

    /**
     * Sends amessage from client
     *
     * @param msg - message to sent
     */
    @Override
    final public void say(String msg) {
        System.out.println("Sent: " + msg);
        out.println(msg);
    }

    /**
     * Used to ping client
     *
     * @return - true if client is connected
     */
    @Override
    final public boolean isAlive() {
        try {
            if (communicate(PING_STRING).equals(PING_STRING)) {
                return true;
            } else {
                socket.close();
            }
        } catch (ConnectionTroubleException | IOException ex) {
            System.out.println("unable to close connection");
        }
        return false;
    }
}
