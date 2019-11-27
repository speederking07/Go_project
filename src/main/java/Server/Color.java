package Server;

public enum Color {
    White(0),
    Black(1);

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
}
