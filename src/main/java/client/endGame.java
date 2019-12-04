package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class endGame extends JDialog implements ActionListener
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel reasonJLabel;
    JLabel yourScoreJLabel;
    JLabel opponentScoreJLabel;
    JButton okJButton;
    JFrame frame;
    public endGame(JFrame frame, String reason, String yourScore, String opponentScore)
    {
        super(frame, "Koniec gry", true);
        this.frame=frame;
        reasonJLabel = new JLabel(reason, JLabel.CENTER);
        yourScoreJLabel = new JLabel("Twoj wynik: " + yourScore, JLabel.CENTER);
        opponentScoreJLabel = new JLabel("Wynik przeciwnika: " + opponentScore, JLabel.CENTER);
        okJButton = new JButton("OK");

        reasonJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        yourScoreJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        opponentScoreJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        okJButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        okJButton.addActionListener(this);

        setLayout(new GridLayout(4,1));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        add(reasonJLabel);
        add(yourScoreJLabel);
        add(opponentScoreJLabel);
        add(okJButton);
        pack();
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("OK"))
        {
            frame.setVisible(false);
            frame.dispose();
            System.exit(0);
        }
    }
}