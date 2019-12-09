package moves;

public class Pass implements Move {
    @Override
    public String toString() {
        return "PASS";
    }

    public String pretty(int board){
        return "Pass";
    }
}
