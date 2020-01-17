package server;

import server.exceprions.SuicideException;
import org.javatuples.Pair;
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

    @Test
    void areaCounter(){
        Map m = new Map(4, "EEEEWWWWBBBBEEEE");
        Pair<Integer, Integer> p = m.getAreaPoints();
        Assertions.assertEquals(4, (int) p.getValue0());
        Assertions.assertEquals(4, (int) p.getValue1());
    }
}