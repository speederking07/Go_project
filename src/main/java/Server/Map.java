package Server;

import Server.Exceprions.IllegalMoveException;
import Server.Exceprions.IllegalPositionException;
import Server.Exceprions.SuicideException;
import org.javatuples.Pair;

import java.util.Arrays;

/**
 * Class representing state of games board
 */
public class Map implements Cloneable {
    private static final char EMPTY_CHAR = 'E';
    private static final char WHITE_CHAR = 'W';
    private static final char BLACK_CHAR = 'B';
    private int size;
    /*
    0 - no checked
    3 - special
    6 - black area
    9 - white area
    12 - contestant area
    3x+1 - black player
    3x+2 - white player
     */
    private int[][] map;
    private int freeW, freeB; // Next free index to chains

    /**
     * Constructor of empty map
     *
     * @param size - board size
     */
    public Map(int size) {
        this.size = size;
        map = new int[size + 2][size + 2];
        Arrays.fill(map[0], 3);
        for (int i = 1; i <= size; i++) {
            Arrays.fill(map[i], 0);
            map[i][0] = 3;
            map[i][size + 1] = 3;
        }
        Arrays.fill(map[size + 1], 3);
        freeW = 2;
        freeB = 1;
    }

    /**
     * Constructor of map by string representation
     *
     * @param size - size of board
     * @param data - string representation of board
     * @throws IllegalArgumentException - in case of wrong string
     */
    public Map(int size, String data) throws IllegalArgumentException {
        this.size = size;
        map = new int[size + 2][size + 2];
        Arrays.fill(map[0], 3);
        if (data.length() != size * size) throw new IllegalArgumentException("Wrong string");
        int characterCounter = 0;
        for (int i = 1; i <= size; i++) {
            map[i][0] = 0;
            for (int j = 1; j <= size; j++) {
                switch (data.charAt(characterCounter)) {
                    case EMPTY_CHAR:
                        map[i][j] = 0;
                        break;
                    case WHITE_CHAR:
                        map[i][j] = 3 * (i * size + j) + 2;
                        break;
                    case BLACK_CHAR:
                        map[i][j] = 3 * (i * size + j) + 1;
                        break;
                    default:
                        throw new IllegalArgumentException("Wrong char");
                }
                characterCounter++;
            }
            map[i][0] = 3;
            map[i][size + 1] = 3;
        }
        Arrays.fill(map[size + 1], 3);
        refactor();
    }

    /**
     * @return - board size
     */
    public int getSize() {
        return size;
    }

    /**
     * Replace of fields with old value to n value
     *
     * @param old - value to be replaced
     * @param n   - value to replaced with
     * @return - number to changes
     */
    private int replace(int old, int n) {
        int counter = 0;
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (map[i][j] == old) {
                    map[i][j] = n;
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Function to replace specific area(group of fields with the same value) with new values
     *
     * @param x   - x position
     * @param y   - y position
     * @param old - value to be replaced
     * @param n   - value to replaced with
     * @return - number to changes
     */
    private int replaceArea(int x, int y, int old, int n) {
        if (map[x][y] != old) return 0;
        map[x][y] = n;
        int counter = 1;
        for (Direction dir : Direction.directions()) {
            counter += replaceArea(x + dir.getX(), y + dir.getY(), old, n);
        }
        return counter;
    }

    /**
     * Computes size of taken area by both players
     *
     * @return - (BlackArea, WhiteArea)
     */
    public Pair<Integer, Integer> getAreaPoints() {
        replace(6, 0);
        replace(9, 0);
        replace(12, 0);
        int blackArea = 0;
        int whiteArea = 0;
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (map[i][j] % 3 == 1) {
                    for (Direction dir : Direction.directions()) {
                        blackArea += replaceArea(i + dir.getX(), j + dir.getY(), 0, 6);
                        whiteArea -= replaceArea(i + dir.getX(), j + dir.getY(), 9, 12);
                    }
                } else if (map[i][j] % 3 == 2) {
                    for (Direction dir : Direction.directions()) {
                        whiteArea += replaceArea(i + dir.getX(), j + dir.getY(), 0, 9);
                        blackArea -= replaceArea(i + dir.getX(), j + dir.getY(), 6, 12);
                    }
                }
            }
        }
        return new Pair<>(blackArea, whiteArea);
    }

    /**
     * @return - string representation of board
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(size * size);
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (map[i][j] % 3 == 0) builder.append(EMPTY_CHAR);
                else if (map[i][j] % 3 == 2) builder.append(WHITE_CHAR);
                else if (map[i][j] % 3 == 1) builder.append(BLACK_CHAR);
            }
        }
        return builder.toString();
    }

    /**
     * Checks if chain has breaths
     *
     * @param x - chain x position
     * @param y - chain y position
     * @return - true if has breaths
     */
    private boolean hasBreaths(int x, int y) {
        int curr = map[x][y];
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (map[i][j] == curr) {
                    for (Direction d : Direction.directions()) {
                        if (map[i + d.getX()][j + d.getY()] % 3 == 0 && map[i + d.getX()][j + d.getY()] != 3)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Connects stones into chains of the same id
     */
    public void refactor() {
        freeW = 2;
        freeB = 1;
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (map[i][j] != 0) {
                    if (map[i][j] % 3 == map[i + 1][j] % 3) {
                        replace(map[i + 1][j], map[i][j]);
                    }
                    if (map[i][j] % 3 == map[i][j + 1] % 3) {
                        replace(map[i][j + 1], map[i][j]);
                    }
                    if (map[i][j] % 3 == 1) freeB = Math.max(freeB, map[i][j]);
                    else freeW = Math.max(freeW, map[i][j]);
                }
            }
        }
        freeW += 3;
        freeB += 3;
    }

    /**
     * Deep cLone map
     *
     * @return - cloned map
     */
    public Map clone() {
        //TODO: Performence to improve
        return new Map(size, toString());
    }

    /**
     * Function to modify map by one stone
     *
     * @param x     - x portion to put stone
     * @param y     - y portion to put stone
     * @param color - color of stone
     * @return - number of "killed" enemy stones
     * @throws IllegalMoveException - if move was invalid
     */
    public int putStone(int x, int y, Color color) throws IllegalMoveException {
        x++;
        y++;
        int points = 0;
        if (x < 1 || x > size || y < 1 || y > size) throw new IllegalPositionException();
        if (map[x][y] != 0) throw new IllegalPositionException();
        boolean suicide = true;
        if (color == Color.White) {
            map[x][y] = freeW;
            for (Direction d : Direction.directions()) {
                if (map[x + d.getX()][y + d.getY()] % 3 == 0 && map[x + d.getX()][y + d.getY()] != 3
                        || map[x + d.getX()][y + d.getY()] % 3 == 2 && hasBreaths(x + d.getX(), y + d.getY()))
                    suicide = false;
                else if (map[x + d.getX()][y + d.getY()] % 3 == 1 && !hasBreaths(x + d.getX(), y + d.getY())) {
                    points += replace(map[x + d.getX()][y + d.getY()], 0);
                    suicide = false;
                }
            }
        } else {
            map[x][y] = freeB;
            for (Direction d : Direction.directions()) {
                if (map[x + d.getX()][y + d.getY()] % 3 == 0 && map[x + d.getX()][y + d.getY()] != 3
                        || map[x + d.getX()][y + d.getY()] % 3 == 1 && hasBreaths(x + d.getX(), y + d.getY()))
                    suicide = false;
                else if ((map[x + d.getX()][y + d.getY()] % 3 == 2) && (!hasBreaths(x + d.getX(), y + d.getY()))) {
                    points += replace(map[x + d.getX()][y + d.getY()], 0);
                    suicide = false;
                }
            }
        }
        if (suicide) throw new SuicideException();
        refactor();
        return points;
    }
}
