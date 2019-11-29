package Client;

import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.junit.Test;

import javax.swing.JButton;
import javax.swing.JFrame;

public class panelBoardTest {
    @Test
    public void boardSizeTest() {
        panelBoard board = new panelBoard(9);
        assertEquals(9, board.boardSize);
        board = new panelBoard(13);
        assertEquals(13, board.boardSize);
        board = new panelBoard(19);
        assertEquals(19, board.boardSize);

    }

    @Test
    public void mousePressedTest() throws AWTException
    {
        panelBoard board = new panelBoard(9);
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(board);
        frame.setVisible(true);
        Robot robot = new Robot();
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        
        JButton simulateButton = new JButton("");
        simulateButton.doClick();
    }
}