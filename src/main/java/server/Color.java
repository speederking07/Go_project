package server;

/**
 * Enum representing color of players
 */
public enum Color {
    White(1),
    Black(0);

    private final int index;

    Color(int ind) {
        this.index = ind;
    }

    /**
     * Get index by color
     *
     * @return - index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Get opposite color
     *
     * @return - opposite color
     */
    public Color getOpposite() {
        if (this == Color.White) return Color.Black;
        return Color.White;
    }

    /**
     * Get string representing color
     *
     * @return - string
     */
    @Override
    public String toString() {
        if (this == Color.White) return "WHITE";
        return "BLACK";
    }
}
