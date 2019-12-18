package server;

import server.exceprions.ConnectionTroubleException;

/**
 * Singleton responsible of matchmaking
 */
final class Queue {
    private volatile static Queue instance;
    private Player waitFor9, waitFor13, waitFor19;

    /**
     * Basic constructor of singleton
     */
    private Queue() {
        waitFor9 = null;
        waitFor13 = null;
        waitFor19 = null;
    }

    /**
     * @return - instance of Queue
     */
    public static Queue getInstance() {
        if (instance == null) {
            synchronized (Queue.class) {
                if (instance == null) {
                    instance = new Queue();
                }
            }
        }
        return instance;
    }

    /**
     * Creates games and starts thread of specific game
     *
     * @param p1   - black player
     * @param p2   - white player
     * @param size - board size
     */
    private synchronized void createGame(Player p1, Player p2, int size) {
        try {
            p1.startGame(Color.Black);
        } catch (ConnectionTroubleException ex) {
            if (size == 9) waitFor9 = p2;
            else if (size == 13) waitFor13 = p2;
            else if (size == 19) waitFor19 = p2;
            return;
        }
        try {
            p2.startGame(Color.White);
        } catch (ConnectionTroubleException ex) {
            p1.endGame("ConnectionTrouble", 0, 0);
            p2.endGame("ConnectionTrouble", 0, 0);
            return;
        }
        Thread t = new Thread(new GoGame(p1, p2, size));
        t.start();
    }

    /**
     * Creates game with another player or puts hit to the queue
     *
     * @param hp   - player
     * @param size - size of board
     */
    public synchronized void makeGameWithPlayer(Player hp, int size) {
        if (size == 9) {
            if (waitFor9 != null) {
                createGame(waitFor9, hp, size);
                waitFor9 = null;
            } else {
                waitFor9 = hp;
            }
        } else if (size == 13) {
            if (waitFor13 != null) {
                createGame(waitFor13, hp, size);
                waitFor13 = null;
            } else {
                waitFor13 = hp;
            }
        } else if (size == 19) {
            if (waitFor19 != null) {
                createGame(waitFor19, hp, size);
                waitFor19 = null;
            } else {
                waitFor19 = hp;
            }
        }
    }

    /**
     * Creates game with bot
     *
     * @param hp   - player
     * @param size - size of board
     */
    public void makeGameWithBot(Player hp, int size) {
        if (size == 9) {
            createGame(hp, new Bot(size), size);
        } else if (size == 13) {
            createGame(hp, new Bot(size), size);
        } else if (size == 19) {
            createGame(hp, new Bot(size), size);
        }
    }
}
