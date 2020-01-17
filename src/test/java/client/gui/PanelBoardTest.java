package client.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

import org.junit.jupiter.api.Test;

import client.gui.PanelBoard;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PanelBoardTest {
    @Test
    public void boardSizeTest() {
        PanelBoard board = new PanelBoard(9);
        assertEquals(9, board.boardSize);
        board = new PanelBoard(13);
        assertEquals(13, board.boardSize);
        board = new PanelBoard(19);
        assertEquals(19, board.boardSize);

    }

    @Test
    public void mousePressedTest() throws AWTException
    {
        PanelBoard board = new PanelBoard(9);
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(board);
        frame.setVisible(true);
        Robot robot = new Robot();
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        
        JButton simulateButton = new JButton("");
        simulateButton.doClick();
    }

    @Test
    public void PanelBoardMethodsTest()
    {
        PanelBoard panel = new PanelBoard(9);

        panel.wrongMove("move");

        char[] positions = "WBEEWBEBW".toCharArray();
        panel.setStonePositions(positions);
        assertEquals(positions, panel.stonePositions);

        panel.changeTurn();
        assertEquals(false, panel.yourTurn);
    }

    @Test
    public void observableTest()
    {
        PanelAction panel = new PanelAction();
        Observer observer = new Observer();
        panel.addObserver(observer);
        panel.notifyObserver("test");
        
        assertEquals(observer, panel.guis.get(0));
        assertEquals("test", observer.message);

        panel.actionPerformed(new ActionEvent(panel, 0, "Spasuj"));
        assertEquals("PASS", observer.message);

        panel.removeObserver(observer);
        assertTrue(panel.guis.isEmpty());
    }

    private class Observer implements ObserverInterface
    {
        public String message;
        @Override
        public void reactOnEvent(String message) {
            this.message=message;
        }

    }
}