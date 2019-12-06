package server;

import server.exceprions.ConnectionTroubleException;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;

class GameTest {
    @Test
    void twoBotsTest() throws InterruptedException {
        Player p1 = new Bot(13);
        Player p2 = new Bot(13);
        try {
            p1.startGame(Color.Black);
            p2.startGame(Color.White);
        } catch (ConnectionTroubleException ex){
            p1.endGame("ConnectionTrouble", 0, 0);
            p2.endGame("ConnectionTrouble", 0, 0);
        }
        Thread t = new Thread(new Game(p1,p2,13));
        t.start();
        t.join();
    }
}