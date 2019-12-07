package client.gui;

public interface GuiInterface
{
    public void wrongMove(String reason);
    public void endGame(String reason, String yourScore, String opponentScore);
    public void changeTurn();
    public void setColor(String color);
    public void setOpponent(String opponent);
    public void setOpponentMove(String move);
    public void setStonePositions(char[] stonePositions);
    public String getStartMessage();
}