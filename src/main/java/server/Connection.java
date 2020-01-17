package server;

import server.exceprions.ConnectionTroubleException;

public interface Connection {

    /**
     * Initialize connection and add game to queue
     */
    void initializeConnection();

    /**
     * Sends and receives message from client
     *
     * @param msg - message to sent
     * @return - received message
     * @throws ConnectionTroubleException - in case to connection troubles
     */
    public String communicate(String msg) throws ConnectionTroubleException;

    /**
     * Receives message from client
     *
     * @return - received message
     * @throws ConnectionTroubleException - in case to connection troubles
     */
    public String listen() throws ConnectionTroubleException;

    /**
     * Sends amessage from client
     *
     * @param msg - message to sent
     */
    public void say(String msg);

    /**
     * Used to ping client
     *
     * @return - true if client is connected
     */
    public boolean isAlive();
}
