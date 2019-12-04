package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class panelBoard extends JPanel
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Image boardImage;
    Image blackPawnImage;
    Image whitePawnImage;
    Point point;
    int boardSize;
    Point[][] places;
    Point nearestPoint;
    int borderPixel;
    int pixelSize;
    client myCLient;
    private char[] stonePositions;
    public panelBoard(int boardSize)
    {
        this.boardSize=boardSize;
        places = new Point[boardSize][boardSize];
        if(boardSize==9)
        {
            boardImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/9x9.png"));
            borderPixel=95;
            pixelSize=700;
        }
        if(boardSize==13)
        {
            boardImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/13x13.png"));
            borderPixel=60;
            pixelSize=767;
        }
        if(boardSize==19)
        {
            boardImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/19x19.png"));
            borderPixel=80;
            pixelSize=1000;
        }
        blackPawnImage=Toolkit.getDefaultToolkit().getImage(getClass().getResource("/blackpawn.png"));
        whitePawnImage=Toolkit.getDefaultToolkit().getImage(getClass().getResource("/whitepawn.png"));
        addMouseListener(new myMouseAdapter());
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int borderHeight=this.getHeight()*borderPixel/pixelSize;
        int borderSpace=(this.getHeight()-2*borderHeight)/(boardSize-1);
        for(int i=0; i<boardSize; i++)
            for(int j=0; j<boardSize; j++)
            {
                places[i][j]= new Point(borderHeight+i*borderSpace,borderHeight+j*borderSpace);
            }
        g.drawImage(boardImage, 0, 0, this.getHeight(), this.getHeight(), this);
        if(stonePositions!=null)
            paintStones(g);
    }

    private void paintStones(Graphics g)
    {
        int stoneNumber=0;
        for(int i=0; i<boardSize; i++)
            for(int j=0; j<boardSize; j++)
            {
                int radius = (int) (panelBoard.this.getHeight()/boardSize/1.30/2); 
                point=new Point(places[i][j].x-radius,places[i][j].y-radius);
                if(stonePositions[stoneNumber]=='B')
                {
                    g.drawImage(blackPawnImage, point.x, point.y,(int) (this.getHeight()/boardSize/1.30),
                                (int) (this.getHeight()/boardSize/1.30), this);
                }
                if(stonePositions[stoneNumber]=='W')
                {
                    g.drawImage(whitePawnImage, point.x, point.y,(int) (this.getHeight()/boardSize/1.30),
                                (int) (this.getHeight()/boardSize/1.30), this);
                }
                stoneNumber++;
            }
    }

    public void setClient(client myClient)
    {
        this.myCLient=myClient;
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
                int x=0,y=0;
                nearestPoint=places[0][0];
                for(int i=0; i<boardSize; i++)
                    for(int j=0; j<boardSize; j++)
                    {
                        if(places[i][j].distance(e.getPoint())<nearestPoint.distance(e.getPoint()))
                        {
                            nearestPoint=places[i][j];
                            x=i;
                            y=j;
                        }
                    }
                if(myCLient!=null)
                {
                    if(myCLient.sendToServer("PUTSTONE " + x + " " + y)==0)
                        myCLient.recieveFromServer();
                }
            }
        }
    }
    public void setStonePositions(char[] stonePositions)
    {
        this.stonePositions=stonePositions;
        repaint();
    }

    public void wrongMove(String[] stringParts)
    {
        JOptionPane.showMessageDialog(this, "Niepoprawny Ruch" + stringParts[1]);                
    }
}