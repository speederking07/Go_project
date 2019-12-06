package server;

import moves.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MoveTest {

    @Test
    void getMove() {
        Assertions.assertEquals(new Pass().toString(), Move.getMove("PASS").toString());
        //Assertions.assertEquals(new GiveUp().toString(), Move.getMove("GIVEUP").toString());
        Assertions.assertEquals(new PutStone(3, 7).toString(), Move.getMove("PUTSTONE 3 7").toString());
    }

    @Test
    void prettyMove() {
        Assertions.assertEquals("Pass", new Pass().pretty());
        Assertions.assertEquals("-", new Empty().pretty());
        Assertions.assertEquals("Kamień na C5", new PutStone(2,4).pretty());
        Assertions.assertEquals("Poddał się", new GiveUp().pretty());
    }

    @Test
    void getMoveEx() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Move.getMove("PUTSTONE A 7"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Move.getMove("PUTS a 7"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Move.getMove("PUTSsONE 2 7"));
    }
}