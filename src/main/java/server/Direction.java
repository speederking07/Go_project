package server;

/**
 * Simple enum to represent direction
 */
public enum Direction {
    N(-1, 0),
    W(0, -1),
    S(1, 0),
    E(0, 1);

    private final int xAx;
    private final int yAx;

    Direction(int x, int y) {
        xAx = x;
        yAx = y;
    }

    /**
     * @return - array of every passable direction
     */
    static Direction[] directions() {
        return new Direction[]{N, W, S, E};
    }

    public int getX() {
        return xAx;
    }

    public int getY() {
        return yAx;
    }
}
