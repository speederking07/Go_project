package Server;

import Moves.Empty;
import Moves.Move;
import Moves.Pass;
import Server.Exceprions.IllegalMoveException;
import Server.Exceprions.KoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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