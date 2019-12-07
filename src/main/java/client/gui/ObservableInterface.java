package client.gui;

public interface ObservableInterface
{
    public void addObserver(ObserverInterface gui);
    public void removeObserver(ObserverInterface gui);
    public void notifyObserver(ObserverInterface gui, String message);

}