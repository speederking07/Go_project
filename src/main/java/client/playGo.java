package Client;
import javax.swing.*;

import java.awt.*;

public class playGo extends JFrame
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    panelBoard myPanelBoard;
    panelAction myPanelAction;
    client goClient;
    public playGo(int boardSize, String opponent)
    {
        myPanelAction=new panelAction(goClient);
        myPanelBoard=new panelBoard(boardSize, goClient);
        setLayout(new BorderLayout());

        add(myPanelBoard, BorderLayout.CENTER);
        add(myPanelAction, BorderLayout.LINE_END);

        goClient.getInstance(myPanelBoard, myPanelAction, this);
        goClient.sendToServer(opponent + "!" + boardSize);
        goClient.sendToServer("WAITING");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


}