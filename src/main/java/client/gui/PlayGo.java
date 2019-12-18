package client.gui;

import javax.swing.*;
import java.awt.*;
import client.connection.*;
import moves.Move;

public class PlayGo extends JFrame implements GuiInterface, ObserverInterface
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    PanelBoardClass myPanelBoard;
    PanelActionClass myPanelAction;
    ConnectionInterface connection;
    String startMessage;
    int boardSize;
    public PlayGo(int boardSize, String opponent, ChooseBoardInterface status)
    {
        this.boardSize=boardSize;
        startMessage = opponent + "!" + boardSize;
        
        myPanelAction=new PanelAction();
        myPanelAction.addObserver(this);
        myPanelBoard=new PanelBoard(boardSize);
        myPanelBoard.addObserver(this);
        
        connection=new ClientConnection(this);
        status.setStatus("Oczekiwanie na gracza");
        connection.startGame();
        setOpponent(opponent);
        
        setLayout(new BorderLayout());
        add(myPanelAction, BorderLayout.LINE_END);
        add(myPanelBoard, BorderLayout.CENTER);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void wrongMove(String reason) {
        myPanelBoard.wrongMove(reason);
    }

    @Override
    public void endGame(String reason, String yourScore, String opponentScore) {
        new EndGame(this, reason, yourScore, opponentScore);
    }

    @Override
    public void changeTurn() {
        myPanelAction.changeTurn();
        myPanelBoard.changeTurn();
    }

    @Override
    public void setColor(String color) {
        myPanelAction.setColor(color);
        this.setVisible(true);
    }

    @Override
    public void setOpponent(String opponent)
    {
        myPanelAction.setOpponent(opponent);
    }

    @Override
    public void setOpponentMove(Move move) {
        myPanelAction.setOpponentMove(move.pretty(boardSize));
    }

    @Override
    public void setStonePositions(char[] stonePositions) {
        myPanelBoard.setStonePositions(stonePositions);
    }

    @Override
    public String getStartMessage()
    {
        return startMessage;
    }

    @Override
    public void reactOnEvent(String message) {
        connection.sendToServer(message);
    }
}
