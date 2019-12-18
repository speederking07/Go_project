package client.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.ActionEvent;

import org.junit.jupiter.api.Test;

public class PanelActionTest
{
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

    @Test
    public void PanelActionMethodsTest()
    {
        PanelAction panel = new PanelAction();

        panel.changeTurn();
        assertEquals("Przeciwnika", panel.whichTurnInfoJLabel.getText());

        panel.setColor("Czarny");
        assertEquals("Czarny", panel.whatColorInfoJLabel.getText());

        panel.setOpponent("BOT");
        assertEquals("Komputerowi", panel.opponentInfoJLabel.getText());

        panel.setOpponentMove("Poddanie");
        assertEquals("Poddanie", panel.opponentMoveJLabel.getText());
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