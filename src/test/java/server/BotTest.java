package server;

import moves.Empty;
import moves.Move;
import server.exceprions.KoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BotTest {

    @Test
    void getMove() {
        Bot bot = new Bot(19);
        Move m = bot.getMove(new Empty(), new Map(19));
        Assertions.assertNotEquals("PASS", m.toString());
    }

    @Test
    void wrongMove() {
        Bot bot = new Bot(9);
        Move m = bot.getMove(new Empty(), new Map(9));
        while (!m.toString().equals("PASS")){
            m = bot.wrongMove(new KoException());
        }
    }
}