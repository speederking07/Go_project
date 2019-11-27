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
    Point[][] places;
    Point nearestPoint;
    int borderPixel;
    int pixelSize;
    public panelBoard(int boardSize)
    {
        this.boardSize=boardSize;
        places = new Point[boardSize][boardSize];
        if(boardSize==9)
        {
            boardImage = Toolkit.getDefaultToolkit().getImage("9x9.png");
            borderPixel=95;
            pixelSize=700;
        }
        if(boardSize==13)
        {
            boardImage = Toolkit.getDefaultToolkit().getImage("13x13.png");
            borderPixel=60;
            pixelSize=767;
        }
        if(boardSize==19)
        {
            boardImage = Toolkit.getDefaultToolkit().getImage("19x19.png");
            borderPixel=80;
            pixelSize=1000;
        }
        blackPawnImage=Toolkit.getDefaultToolkit().getImage("blackpawn.png");
        whitePawnImage=Toolkit.getDefaultToolkit().getImage("whitepawn.png");
        addMouseListener(new myMouseAdapter());
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //int borderWidth=this.getWidth()*52/693;
        int borderHeight=this.getHeight()*borderPixel/pixelSize;
        int borderSpace=(this.getHeight()-2*borderHeight)/(boardSize-1);
        for(int i=0; i<boardSize; i++)
            for(int j=0; j<boardSize; j++)
            {
                places[i][j]= new Point(borderHeight+i*borderSpace,borderHeight+j*borderSpace);
            }
        g.drawImage(boardImage, 0, 0, this.getHeight(), this.getHeight(), this);
        if(point!=null)
            g.drawImage(blackPawnImage, point.x, point.y,(int) (this.getHeight()/boardSize/1.30),
                        (int) (this.getHeight()/boardSize/1.30), this);
    }

    private class myMouseAdapter extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            if(e.getButton()==MouseEvent.BUTTON1)
            {
                //90 700
                //60 767
                nearestPoint=places[0][0];
                for(int i=0; i<boardSize; i++)
                    for(int j=0; j<boardSize; j++)
                    {
                        if(places[i][j].distance(e.getPoint())<nearestPoint.distance(e.getPoint()))
                            nearestPoint=places[i][j];
                    }
                int radius = (int) (panelBoard.this.getHeight()/boardSize/1.30/2); 
                point=new Point(nearestPoint.x-radius,nearestPoint.y-radius);
                repaint();
            }
        }
    }
}