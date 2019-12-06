package moves;

public class GiveUp implements Move {
    @Override
    public String toString() {
        return "GIVEUP";
    }

    public String pretty(){
        return "Poddał się";
    }
}
