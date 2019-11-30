package Client;

import static org.junit.Assert.assertEquals;

import java.awt.event.ActionEvent;

import org.junit.Test;


public class chooseBoardTest
{
    @Test
    public void isPressedTest()   
    {
        chooseBoard frame = new chooseBoard();
        
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
        chooseBoard frame = new chooseBoard();

        frame.actionPerformed(new ActionEvent(frame, 0, "Graj"));
        
        assertEquals(true, frame.isVisible());

        frame.botJToggleButton.setSelected(true);
        frame.actionPerformed(new ActionEvent(frame, 0, "Bot"));
        frame.actionPerformed(new ActionEvent(frame, 0, "Graj"));

        assertEquals(true, frame.isVisible());
    }

}