package Server;

import java.util.Arrays;
import java.util.Iterator;

public enum Direction {
    N(-1,0),
    W(0, -1),
    S(1, 0),
    E(0, 1),
    None(0, 0);

    private final int XAx;
    private final int YAx;

    Direction(int x, int y){
        XAx = x;
        YAx = y;
    }

    public int getX() {
        return XAx;
    }

    public int getY() {
        return YAx;
    }

    static Direction[] directions(){
        Direction[] array = {N,W,S,E};
        return array;
    }
}
