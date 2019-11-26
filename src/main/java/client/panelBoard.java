import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class panelBoard extends JPanel
{
    Image boardImage;
    Image blackPawnImage;
    Image whitePawnImage;
    Point point;
    int boardSize;
    public panelBoard(int boardSize)
    {
        this.boardSize=boardSize;
        if(boardSize==9)
            boardImage = Toolkit.getDefaultToolkit().getImage("9x9.png");
        if(boardSize==13)
            boardImage = Toolkit.getDefaultToolkit().getImage("13x13.png");
        if(boardSize==19)
            boardImage = Toolkit.getDefaultToolkit().getImage("19x19.png");
        blackPawnImage=Toolkit.getDefaultToolkit().getImage("blackpawn.png");
        whitePawnImage=Toolkit.getDefaultToolkit().getImage("whitepawn.png");
        addMouseListener(new myMouseAdapter());
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(boardImage, 0, 0, this.getWidth(), this.getHeight(), this);
        if(point!=null)
            g.drawImage(blackPawnImage, point.x, point.y,(int) (this.getWidth()/boardSize/1.25),
                        (int) (this.getWidth()/boardSize/1.25), this);
    }

    private class myMouseAdapter extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            if(e.getButton()==MouseEvent.BUTTON1)
            {
                //52 693
                int radius = (int) (panelBoard.this.getWidth()/boardSize/1.25/2); 
                point=new Point(e.getX()-radius,e.getY()-radius);
                repaint();
            }
        }
    }
}