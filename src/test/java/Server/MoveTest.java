package Server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void getMove() {
        Assertions.assertEquals(new Pass().toString(), Move.getMove("PASS").toString());
        //Assertions.assertEquals(new GiveUp().toString(), Move.getMove("GIVEUP").toString());
        Assertions.assertEquals(new PutStone(3, 7).toString(), Move.getMove("PUTSTONE 3 7").toString());
    }

    @Test
    void getMoveEx() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Move.getMove("PUTSTONE A 7"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Move.getMove("PUTS a 7"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Move.getMove("PUTSsONE 2 7"));
    }
}