package client.gui;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class PanelAction extends PanelActionClass implements ActionListener
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel actionInfoJLabel;
    JLabel opponentMoveJLabel;
    JLabel opponentJLabel;
    JLabel opponentInfoJLabel;
    JLabel whichTurnJLabel;
    JLabel whichTurnInfoJLabel;
    JLabel whatColorJLabel;
    JLabel whatColorInfoJLabel;
    JButton passJButton;
    JButton surrenderJButton;
    List<ObserverInterface> guis;
    public PanelAction()
    {
        guis = new ArrayList<>();
        whatColorJLabel = new JLabel("Twoj kolor to: ", JLabel.CENTER);
        whatColorInfoJLabel = new JLabel("", JLabel.CENTER);
        opponentJLabel = new JLabel("Grasz przeciwko:",JLabel.CENTER);
        opponentInfoJLabel = new JLabel("",JLabel.CENTER);
        actionInfoJLabel = new JLabel("Ostatni ruch przeciwnika:",JLabel.CENTER);
        opponentMoveJLabel = new JLabel("",JLabel.CENTER);
        whichTurnJLabel = new JLabel("Teraz ruch:",JLabel.CENTER);
        whichTurnInfoJLabel = new JLabel("Twoj",JLabel.CENTER);

        whatColorJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        whatColorInfoJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        opponentJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        opponentInfoJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
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
        setLayout(new GridLayout(5,2));

        add(opponentJLabel);
        add(opponentInfoJLabel);
        add(whatColorJLabel);
        add(whatColorInfoJLabel);
        add(actionInfoJLabel);
        add(opponentMoveJLabel);
        add(whichTurnJLabel);
        add(whichTurnInfoJLabel);
        add(passJButton);
        add(surrenderJButton);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("Spasuj"))
            notifyObserver("PASS");
        if(e.getActionCommand().equals("Poddaj się"))
            notifyObserver("GIVEUP");
    }

    
    @Override
    public void changeTurn()
    {
        if(whichTurnInfoJLabel.getText().equals("Twoj"))
            whichTurnInfoJLabel.setText("Przeciwnika");
        else
            whichTurnInfoJLabel.setText("Twoj");
    }

    @Override
    public void setColor(String color)
    {
        whatColorInfoJLabel.setText(color);
    }
    
    @Override
    public void setOpponent(String opponent)
    {
        if(opponent.equals("BOT"))
            opponent = "Komputerowi";
        else
            opponent = "Graczowi";
        opponentInfoJLabel.setText(opponent);
    }

    @Override
    public void setOpponentMove(String move)
    {
        opponentMoveJLabel.setText(move);
    }

    @Override
    public void addObserver(ObserverInterface gui) {
        guis.add(gui);
    }

    @Override
    public void removeObserver(ObserverInterface gui) {
        guis.remove(gui);
    }

    @Override
    public void notifyObserver(String message) {
        for(ObserverInterface obs : guis )
            obs.reactOnEvent(message);
    }
}