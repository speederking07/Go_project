package Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;

import org.junit.Test;

public class endGameTest {
    @Test
    public void endTest() throws IOException
    {
        endGame end = new endGame(new JFrame(), "reason", "1", "1");
        end.actionPerformed(new ActionEvent(end, 0, "OK"));
        assertEquals(false, end.isVisible());
    }
}