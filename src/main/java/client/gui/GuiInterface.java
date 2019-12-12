package client.gui;

import moves.Move;

public interface GuiInterface
{
    public void wrongMove(String reason);
    public void endGame(String reason, String yourScore, String opponentScore);
    public void changeTurn();
    public void setColor(String color);
    public void setOpponent(String opponent);
    public void setOpponentMove(Move move);
    public void setStonePositions(char[] stonePositions);
    public String getStartMessage();
}