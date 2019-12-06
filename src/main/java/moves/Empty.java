package moves;

public class Empty implements Move {
    @Override
    public String toString() {
        return "EMPTY";
    }

    public String pretty(){
        return "-";
    }
}
