package Server.Exceprions;

public class IllegalPositionException extends IllegalMoveException {
    @Override
    public String toString() {
        return "ILLEGALPOSITION";
    }
}
