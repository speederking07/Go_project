package server;

public enum Owner {
    White(0, Owner.Black),
    Black(1, Owner.White),
    None(-1, Owner.None);

    private final int index;
    private final Owner opposite;

    Owner(int ind, Owner opp) {
        this.index = ind;
        this.opposite = opp;
    }

    public int getIndex() {
        return this.index;
    }

    public Owner getOpposite() {
        return opposite;
    }

}
