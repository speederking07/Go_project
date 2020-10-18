package client.gui;

import javax.swing.JPanel;

public abstract class PanelBoardClass extends JPanel implements ObservableInterface
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    abstract public void wrongMove(String reason);
    abstract public void changeTurn();
    abstract public void setStonePositions(char[] stonePositions);
}