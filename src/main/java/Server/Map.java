package Server;

import Server.Exceprions.IllegalMoveException;
import Server.Exceprions.IllegalPositionException;
import Server.Exceprions.SuicideException;

import java.util.Arrays;

public class Map implements Cloneable {
    private static final char emptyChar = 'E';
    private static final char whiteChar = 'W';
    private static final char blackChar = 'B';
    private int size;
    /*
    0 - empty
    3 - border
    3x+1 - black player
    3x+2 - white player
     */
    private int[][] map;
    private int freeW, freeB;

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
                    case emptyChar:
                        map[i][j] = 0;
                        break;
                    case whiteChar:
                        map[i][j] = 3 * (i*size + j) + 2;
                        break;
                    case blackChar:
                        map[i][j] = 3 * (i*size + j) + 1;
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


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(size * size);
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (map[i][j] == 0) builder.append(emptyChar);
                else if (map[i][j] % 3 == 2) builder.append(whiteChar);
                else if (map[i][j] % 3 == 1) builder.append(blackChar);
                else builder.append('#');
            }
        }
        return builder.toString();
    }

    private boolean hasBreaths(int x, int y) {
        int curr = map[x][y];
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (map[i][j] == curr) {
                    for (Direction d : Direction.directions()) {
                        if (map[i + d.getX()][j + d.getY()] == 0) return true;
                    }
                }
            }
        }
        return false;
    }

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
                    if(map[i][j] % 3 == 1) freeB = Math.max(freeB, map[i][j]);
                    else freeW = Math.max(freeW, map[i][j]);
                }
            }
        }
        freeW += 3;
        freeB += 3;
    }

    public Map clone(){
        return new Map(size, toString());
    }

    public int putStone(int x, int y, Color color) throws IllegalMoveException {
        x++;
        y++;
        int points = 0;
        if (x < 1 || x >= size || y < 1 || y >= size) throw new IllegalPositionException();
        if (map[x][y] != 0) throw new IllegalPositionException();
        boolean suicide = true;
        if (color == Color.White) {
            map[x][y] = freeW;
            for (Direction d : Direction.directions()) {
                if (map[x + d.getX()][y + d.getY()] == 0) suicide = false;
                else if (map[x + d.getX()][y + d.getY()] % 3 == 1 && !hasBreaths(x + d.getX(), y + d.getY())) {
                    points += replace(map[x + d.getX()][y + d.getY()], 0);
                    suicide = false;
                }
            }
        } else {
            map[x][y] = freeB;
            for (Direction d : Direction.directions()) {
                if (map[x + d.getX()][y + d.getY()] == 0) suicide = false;
                else if (map[x + d.getX()][y + d.getY()] % 3 == 2 && !hasBreaths(x + d.getX(), y + d.getY())) {
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
