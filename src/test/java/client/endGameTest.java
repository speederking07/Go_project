package Client;


import java.io.IOException;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

public class endGameTest {

    @Test
    public void endTest() throws IOException
    {
        new endGame(new JFrame(), "reason", "1", "1");
    }
}