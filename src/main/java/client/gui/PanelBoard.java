package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class PanelBoard extends PanelBoardClass
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Image boardImage;
    Image blackPawnImage;
    Image whitePawnImage;
    Point point;
    public int boardSize;
    Point[][] places;
    Point nearestPoint;
    int borderPixel;
    int pixelSize;
    List<ObserverInterface> guis;
    char[] stonePositions;
    boolean yourTurn;
    public PanelBoard(int boardSize)
    {
        guis = new ArrayList<>();
        this.boardSize=boardSize;
        yourTurn=true;
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
        setBackground(new Color(129, 215, 219));
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
                int radius = (int) (PanelBoard.this.getHeight()/boardSize/1.30/2); 
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

    private class myMouseAdapter extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            if(e.getButton()==MouseEvent.BUTTON1 && yourTurn)
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
                    notifyObserver("PUTSTONE " + x + " " + y);
            }
        }
    }

    @Override
    public void setStonePositions(char[] stonePositions)
    {
        this.stonePositions=stonePositions;
        repaint();
    }

    @Override
    public void wrongMove(String reason)
    {
        JOptionPane.showMessageDialog(this, "Niepoprawny Ruch" + reason);                
    }

    @Override
    public void changeTurn()
    {
        if(yourTurn)
            yourTurn=false;
        else
            yourTurn=true;
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