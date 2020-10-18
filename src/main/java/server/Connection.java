package server;

import server.exceprions.ConnectionTroubleException;

public interface Connection extends Runnable {

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

    /**
     * Thread body used to initialize game on server
     */
    @Override
    default void run() {
        try {
            String ans = communicate("HEY");
            String[] data = ans.split("!");
            if (data.length != 2) {
                throw new IllegalArgumentException();
            }
            if (data[0].equals("HUMAN")) {
                Queue.getInstance().makeGameWithPlayer(new HumanPlayer(this), Integer.parseInt(data[1]));
            } else if (data[0].equals("BOT")) {
                Queue.getInstance().makeGameWithBot(new HumanPlayer(this), Integer.parseInt(data[1]));
            } else throw new IllegalArgumentException();
        } catch (ConnectionTroubleException ex) {
            System.out.println("Conection truble ");
        } catch (IllegalArgumentException ex) {
            System.out.println("Wrong data when selecting game");
        }
    }
}
