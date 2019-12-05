package Moves;

public interface Move {
    /**
     * Changes string to Move instance
     * @param s - string representation
     * @return - Move instance
     * @throws IllegalArgumentException - in case of invalid string
     */
    static Move getMove(String s) throws IllegalArgumentException{
        if(s.equals("PASS")) return new Pass();
        else if (s.equals("GIVEUP")) return new GiveUp();
        else if (s.equals("EMPTY")) return new Empty();
        else{
            String[] data = s.split(" ");
            if(data[0].equals("PUTSTONE")) {
                if (data.length != 3) throw new IllegalArgumentException("Wrong number of arguments");
                int x = Integer.parseInt(data[1]);
                int y = Integer.parseInt(data[2]);
                return new PutStone(x, y);
            }
            else throw new IllegalArgumentException("Wrong move");
        }
    }

    /**
     * Generates string to display
     * @return - pretty string
     */
    public String pretty();
}
