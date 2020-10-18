package server.exceprions;

public class KoException extends IllegalMoveException {
    @Override
    public String toString() {
        return "KO";
    }
}
