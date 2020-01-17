package client.gui;

import java.io.IOException;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import client.gui.EndGame;

public class EndGameTest {

    @Test
    public void endTest() throws IOException
    {
        new EndGame(new JFrame(), "reason", "1", "1");
    }
}