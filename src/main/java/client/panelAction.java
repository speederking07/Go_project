package Client;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

public class panelAction extends JPanel implements ActionListener
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel actionInfoJLabel;
    JLabel opponentMoveJLabel;
    JLabel whichTurnJLabel;
    JLabel whichTurnInfoJLabel;
    JLabel whichColorJLabel;
    JLabel whichColorInfoJLabel;
    JButton passJButton;
    JButton surrenderJButton;
    client myClient;
    public panelAction()
    {
        whichColorJLabel = new JLabel("Twoj kolor to: ", JLabel.CENTER);
        whichColorInfoJLabel = new JLabel("", JLabel.CENTER);
        actionInfoJLabel = new JLabel("Ostatni ruch przeciwnika",JLabel.CENTER);
        opponentMoveJLabel = new JLabel("",JLabel.CENTER);
        whichTurnJLabel = new JLabel("Teraz ruch",JLabel.CENTER);
        whichTurnInfoJLabel = new JLabel("",JLabel.CENTER);

        whichColorJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        whichColorInfoJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        actionInfoJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        opponentMoveJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        whichTurnJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        whichTurnInfoJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        passJButton=new JButton("Spasuj");
        surrenderJButton=new JButton("Poddaj si\u0119");

        passJButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        surrenderJButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        passJButton.addActionListener(this);
        surrenderJButton.addActionListener(this);

        setBackground(new Color(129, 215, 219));
        setLayout(new GridLayout(8,1));
        add(whichColorJLabel);
        add(whichColorInfoJLabel);
        add(actionInfoJLabel);
        add(opponentMoveJLabel);
        add(whichTurnJLabel);
        add(whichTurnInfoJLabel);
        add(passJButton);
        add(surrenderJButton);
    }

    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("Spasuj"))
            myClient.sendToServer("PASS");
        if(e.getActionCommand().equals("Poddaj się"))
            myClient.sendToServer("GIVEUP");
    }
    
    public void setWhichTurn(String whichTurn)
    {
        whichTurnInfoJLabel.setText(whichTurn);
    }
    public void setColor(String color)
    {
        whichColorInfoJLabel.setText(color);
    }

    public void setOpponentMove(String move)
    {
        opponentMoveJLabel.setText(move);
    }
    public void setClient(client myClient)
    {
        this.myClient=myClient;
    }
}