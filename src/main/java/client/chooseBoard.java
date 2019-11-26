import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class chooseBoard extends JFrame implements ActionListener
{
    JLabel chooseBoardJLabel;
    JButton JButton9x9;
    JButton JButton13x13;
    JButton JButton19x19;
    public chooseBoard()
    {
        chooseBoardJLabel = new JLabel("Wybierz rozmiar",JLabel.CENTER);

        JButton9x9=new JButton("9x9");
        JButton13x13=new JButton("13x13");
        JButton19x19=new JButton("19x19");

        chooseBoardJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        JButton9x9.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        JButton13x13.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        JButton19x19.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        JButton9x9.addActionListener(this);
        JButton13x13.addActionListener(this);
        JButton19x19.addActionListener(this);

        add(chooseBoardJLabel);
        add(JButton9x9);
        add(JButton13x13);
        add(JButton19x19);

        setLayout(new GridLayout(4,1));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("9x9"))
            searchPlayers(9);
        if(e.getActionCommand().equals("13x13"))
            searchPlayers(13);
        if(e.getActionCommand().equals("19x19"))
            searchPlayers(19);

    }

    private void searchPlayers(int boardSize)
    {
        setVisible(false);
        new playGo(boardSize);
    }

    public static void main(String args[])
    {
        chooseBoard cg = new chooseBoard();
    }
}