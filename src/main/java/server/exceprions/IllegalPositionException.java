package server.exceprions;

public class IllegalPositionException extends IllegalMoveException {
    @Override
    public String toString() {
        return "ILLEGALPOSITION";
    }
}
