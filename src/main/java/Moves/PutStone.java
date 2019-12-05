package Moves;

public class PutStone implements Move {
    private int x, y;

    public PutStone(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "PUTSTONE" +
                " " + x +
                " " + y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String pretty(){
        return "Kamień na "+(char)(65 + x)+y;
    }
}
