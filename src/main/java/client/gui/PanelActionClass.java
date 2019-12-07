package client.gui;

import javax.swing.JPanel;

public abstract class PanelActionClass extends JPanel implements ObservableInterface
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    abstract public void changeTurn();
    abstract public void setColor(String color);
    abstract public void setOpponent(String opponent);
    abstract public void setOpponentMove(String move);
}