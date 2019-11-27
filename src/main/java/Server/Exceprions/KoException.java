package Server.Exceprions;

public class KoException extends IllegalMoveException {
    @Override
    public String toString() {
        return "KO";
    }
}
