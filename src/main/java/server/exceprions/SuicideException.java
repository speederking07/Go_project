package server.exceprions;

public class SuicideException extends IllegalMoveException {
    @Override
    public String toString() {
        return "SUICIDE";
    }
}
