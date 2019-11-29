package Server.Moves;

public class PutStone implements Move {
    public int x, y;

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
}
