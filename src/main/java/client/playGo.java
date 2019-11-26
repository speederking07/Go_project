import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class playGo extends JFrame
{
    panelBoard myPanelBoard;
    panelAction myPanelAction;
    public playGo(int boardSize)
    {
        myPanelBoard=new panelBoard(boardSize);
        myPanelAction=new panelAction();
        setLayout(new BorderLayout());
        add(myPanelBoard, BorderLayout.CENTER);
        add(myPanelAction, BorderLayout.LINE_END);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}