package Server;

import Server.Exceprions.ConnectionTroubleException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable{
    private static final String PING_STRING = "PING";
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    Connection(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        Thread thread = new Thread(this);
        thread.start();
    }

    final public String communicate(String msg) throws ConnectionTroubleException {
        out.println(msg);
        System.out.println("Sent: "+msg);
        try {
            String s = in.readLine();
            System.out.println("Received: "+s);
            return s;

        } catch (IOException e) {
            System.out.println("Read failed");
            throw new ConnectionTroubleException();
        }
    }

    final public void say(String msg) {
        System.out.println("Sent: "+msg);
        out.println(msg);
    }

    final public boolean isAlive() {
        try {
            if (communicate(PING_STRING).equals(PING_STRING)) return true;
            else socket.close();
        } catch (ConnectionTroubleException | IOException ex) {
            System.out.println("unable to close connection");
        }
        return false;
    }

    @Override
    final public void run() {
        try {
            String ans = communicate("HEY");
            String[] data = ans.split("!");
            if(data.length != 2) throw new IllegalArgumentException();
            if (data[0].equals("HUMAN")){
                Queue.getInstance().makeGameWithPlayer(new HumanPlayer(this), Integer.parseInt(data[1]));
            }
            else if (data[0].equals("BOT")){
                Queue.getInstance().makeGameWithBot(new HumanPlayer(this), Integer.parseInt(data[1]));
            }
            else throw new IllegalArgumentException();
        }catch (ConnectionTroubleException ex){
            System.out.println("conection truble ");
        }catch (IllegalArgumentException ex){
            System.out.println("Wrong data when selecting game");
        }
    }
}
