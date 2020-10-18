package server;

import org.java_websocket.WebSocket;
import server.exceprions.ConnectionTroubleException;

import java.util.ArrayList;
import java.util.List;

public class WebConnection implements Connection {
    List<String> inputs;
    WebSocket socket;
    boolean alive;

    WebConnection(WebSocket s){
        alive = true;
        socket = s;
        inputs = new ArrayList<String>();
    }

    @Override
    public void initializeConnection() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public String communicate(String msg) throws ConnectionTroubleException {
        say(msg);
        return listen();
    }

    @Override
    public String listen() throws ConnectionTroubleException {
        while (inputs.isEmpty()) {
            try {
                if(alive == false) throw new ConnectionTroubleException();
                Thread.sleep(10);
            }catch (InterruptedException ex){
            }
        }
        String msg = inputs.remove(0);
        System.out.println("Received: " + msg);
        return msg;
    }

    @Override
    public void say(String msg) {
        if(alive) {
            System.out.println("Sent: " + msg);
            socket.send(msg);
        }
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    void addToQueue(String s){
        inputs.add(s);
    }

    void kill(){
        alive = false;
    }
}
