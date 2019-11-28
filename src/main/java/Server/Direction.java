package Server;

public enum Direction {
    N(-1, 0),
    W(0, -1),
    S(1, 0),
    E(0, 1);

    private final int XAx;
    private final int YAx;

    Direction(int x, int y) {
        XAx = x;
        YAx = y;
    }

    static Direction[] directions() {
        return new Direction[]{N, W, S, E};
    }

    public int getX() {
        return XAx;
    }

    public int getY() {
        return YAx;
    }
}
