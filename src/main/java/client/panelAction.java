import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class panelAction extends JPanel
{
    JLabel actionInfoJLabel;
    JLabel actionOpponentJLabel;
    JLabel whichTurnJLabel;
    JLabel whichTurnInfoJLabel;
    JLabel whichColorInfoJLabel;
    JButton passJButton;
    JButton surrenderJButton;
    public panelAction()
    {
        whichColorInfoJLabel = new JLabel("Jeste\u015B: ", JLabel.CENTER);
        actionInfoJLabel = new JLabel("Ostatni ruch przeciwnika",JLabel.CENTER);
        actionOpponentJLabel = new JLabel("",JLabel.CENTER);
        whichTurnJLabel = new JLabel("Teraz ruch",JLabel.CENTER);
        whichTurnInfoJLabel = new JLabel("Twoj",JLabel.CENTER);

        whichColorInfoJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        actionInfoJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        actionOpponentJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        whichTurnJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        whichTurnInfoJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        passJButton=new JButton("Spasuj");
        surrenderJButton=new JButton("Poddaj si\u0119");

        passJButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        surrenderJButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        setBackground(new Color(129, 215, 219));
        setLayout(new GridLayout(7,1));
        add(whichColorInfoJLabel);
        add(actionInfoJLabel);
        add(actionOpponentJLabel);
        add(whichTurnJLabel);
        add(whichTurnInfoJLabel);
        add(passJButton);
        add(surrenderJButton);
    }
}