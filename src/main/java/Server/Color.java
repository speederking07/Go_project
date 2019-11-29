package Server;

public enum Color {
    White(1),
    Black(0);

    private final int index;

    Color(int ind) {
        this.index = ind;
    }

    public int getIndex() {
        return this.index;
    }

    public Color getOpposite() {
        if (this == Color.White) return Color.Black;
        return Color.White;
    }

    @Override
    public String toString() {
        if (this == Color.White) return "WHITE";
        return "BLACK";
    }
}
