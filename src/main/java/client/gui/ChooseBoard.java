package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChooseBoard extends JFrame implements ActionListener, Runnable, ChooseBoardInterface
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel chooseBoardJLabel;
    JLabel choosePlayerJLabel;
    JLabel statusJLabel;
    JLabel statusInfoJLabel;
    JLabel displayHelpJLabel;
    JToggleButton JToggleButton9x9;
    public JToggleButton JToggleButton13x13;
    public JToggleButton JToggleButton19x19;
    public JToggleButton botJToggleButton;
    public JToggleButton playerJToggleButton;
    JButton playJButton;
    boolean sizeSelected;
    boolean playerSelected;
    int boardSize;
    String opponent;
    public ChooseBoard()
    {
        sizeSelected=false;
        playerSelected=false;

        chooseBoardJLabel = new JLabel("Wybierz rozmiar",JLabel.CENTER);
        choosePlayerJLabel = new JLabel("Wybierz przeciwnika",JLabel.CENTER);
        statusJLabel = new JLabel("Status",JLabel.CENTER);
        statusInfoJLabel = new JLabel("",JLabel.CENTER);
        displayHelpJLabel=new JLabel();

        JToggleButton9x9=new JToggleButton("9x9");
        JToggleButton13x13=new JToggleButton("13x13");
        JToggleButton19x19=new JToggleButton("19x19");
        botJToggleButton=new JToggleButton("Bot");
        playerJToggleButton=new JToggleButton("Gracz");
        playJButton=new JButton("Graj");

        chooseBoardJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        choosePlayerJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        statusJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        statusInfoJLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        JToggleButton9x9.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        JToggleButton13x13.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        JToggleButton19x19.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        botJToggleButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        playerJToggleButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        playJButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        JToggleButton9x9.addActionListener(this);
        JToggleButton13x13.addActionListener(this);
        JToggleButton19x19.addActionListener(this);
        botJToggleButton.addActionListener(this);
        playerJToggleButton.addActionListener(this);
        playJButton.addActionListener(this);

        add(chooseBoardJLabel);
        add(choosePlayerJLabel);
        add(statusJLabel);
        add(JToggleButton9x9);
        add(botJToggleButton);
        add(statusInfoJLabel);
        add(JToggleButton13x13);
        add(playerJToggleButton);
        add(displayHelpJLabel);
        add(JToggleButton19x19);
        add(playJButton);

        setLayout(new GridLayout(4,3));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("9x9"))
        {
            JToggleButton13x13.setSelected(false);
            JToggleButton19x19.setSelected(false);
            if(JToggleButton9x9.isSelected())
                sizeSelected=true;
            else 
                sizeSelected=false;
            boardSize=9;
        }
        if(e.getActionCommand().equals("13x13"))
        {
            JToggleButton9x9.setSelected(false);
            JToggleButton19x19.setSelected(false);
            if(JToggleButton13x13.isSelected())
                sizeSelected=true;
            else 
                sizeSelected=false;
            boardSize=13;
        }
        if(e.getActionCommand().equals("19x19"))
        {
            JToggleButton9x9.setSelected(false);
            JToggleButton13x13.setSelected(false);
            if(JToggleButton19x19.isSelected())
                sizeSelected=true;
            else 
                sizeSelected=false;
            boardSize=19;
        }
        if(e.getActionCommand().equals("Bot"))
        {
            playerJToggleButton.setSelected(false);
            if(botJToggleButton.isSelected())
                playerSelected=true;
            else
                playerSelected=false;
            opponent="BOT";
        }
        if(e.getActionCommand().equals("Gracz"))
        {
            botJToggleButton.setSelected(false);
            if(playerJToggleButton.isSelected())
                playerSelected=true;
            else
                playerSelected=false;
            opponent="HUMAN";
        }
        if(e.getActionCommand().equals("Graj"))
        {
            if(sizeSelected && playerSelected)
            {
                Thread thread = new Thread(this);
                thread.start();
            }
            if(!sizeSelected)
                JOptionPane.showMessageDialog(this, "Wybierz rozmiar");                
            if(!playerSelected)
                JOptionPane.showMessageDialog(this, "Wybierz gracza");                
        }
    }

    @Override
    public void run()
    {
        setStatus("Laczenie z serwerem");
        new PlayGo(boardSize, opponent, this);
        setVisible(false);
    }

    @Override
    public void setStatus(String status)
    {
        statusInfoJLabel.setText(status);
    }

    public static void main(String args[])
    {
        new ChooseBoard();
    }
}