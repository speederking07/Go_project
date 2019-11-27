package Server;

import Server.Exceprions.SuicideException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MapTest {

    @Test
    void creationTest(){
        Map m = new Map(3, "EEWEEEEEE");
        Assertions.assertEquals("EEWEEEEEE", m.toString());

        m = new Map(4, "EEWEEBBEEEEWBEEW");
        Assertions.assertEquals("EEWEEBBEEEEWBEEW", m.toString());
    }

    @Test
    void wrongDataTest(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Map map = new Map(5, "EEE");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Map(3, "EEWEEEEDE");
        });
    }

    @Test
    void putStoneTest(){
        Map m = new Map(3, "EEEEEEEEE");
        try {
            m.putStone(0, 0, Color.White);
        }
        catch (Exception e){
            Assertions.fail();
        }
        Assertions.assertEquals("WEEEEEEEE", m.toString());
        try {
            m.putStone(1, 0, Color.White);
            m.putStone(0, 1, Color.White);
        }
        catch (Exception e){
            Assertions.fail();
        }
        Assertions.assertEquals("WWEWEEEEE", m.toString());
        Map m2 = new Map(3, "EWWWWWBBB");
        try {
            m2.putStone(0, 0, Color.Black);
        }catch (Exception e){
            Assertions.fail();
        }
        Assertions.assertEquals("BEEEEEBBB", m2.toString());
    }

    @Test
    void failPutStoneTest() {
        Map m = new Map(3, "EWWWWWEEE");
        Assertions.assertThrows(SuicideException.class, () -> {
            m.putStone(0, 0, Color.Black);
        });

    }
}