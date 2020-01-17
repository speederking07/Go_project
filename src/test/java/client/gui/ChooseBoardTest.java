package client.gui;

import static org.junit.Assert.assertEquals;

import java.awt.event.ActionEvent;

import org.junit.jupiter.api.Test;

import client.gui.ChooseBoard;


public class ChooseBoardTest
{
    @Test
    public void isPressedTest()   
    {
        ChooseBoard frame = new ChooseBoard();
        
        assertEquals(true, frame.isVisible());

        frame.actionPerformed(new ActionEvent(frame, 0, "13x13"));
        frame.actionPerformed(new ActionEvent(frame, 0, "19x19"));
        frame.actionPerformed(new ActionEvent(frame, 0, "9x9"));

        assertEquals(false, frame.JToggleButton13x13.isSelected());
        assertEquals(false, frame.JToggleButton19x19.isSelected());

        frame.actionPerformed(new ActionEvent(frame, 0, "Gracz"));
        frame.actionPerformed(new ActionEvent(frame, 0, "Bot"));

        assertEquals(false, frame.playerJToggleButton.isSelected());
        
    }
    //validated
    /*@Test
    public void isNotVisibleTest()
    {
        chooseBoard frame = new chooseBoard();

        frame.JToggleButton9x9.setSelected(true);
        frame.botJToggleButton.setSelected(true);
        frame.actionPerformed(new ActionEvent(frame, 0, "9x9"));
        frame.actionPerformed(new ActionEvent(frame, 0, "Bot"));
        frame.actionPerformed(new ActionEvent(frame, 0, "Graj"));

        assertEquals(false, frame.isVisible());
    }*/

    @Test
    public void isVisibleTest()
    {
        ChooseBoard frame = new ChooseBoard();

        frame.actionPerformed(new ActionEvent(frame, 0, "Graj"));
        
        assertEquals(true, frame.isVisible());

        frame.botJToggleButton.setSelected(true);
        frame.actionPerformed(new ActionEvent(frame, 0, "Bot"));
        frame.actionPerformed(new ActionEvent(frame, 0, "Graj"));

        assertEquals(true, frame.isVisible());
    }

}