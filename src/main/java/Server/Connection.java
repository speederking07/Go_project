package Server;

import Server.Exceprions.ConnectionTroubleException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable{
    private static final String pingString = "PING";
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private Thread thread;

    Connection(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        thread = new Thread(this);
        thread.start();
    }

    public String communicate(String msg) throws ConnectionTroubleException {
        out.println(msg);
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println("Read failed");
            throw new ConnectionTroubleException();
        }
    }

    public void say(String msg) throws ConnectionTroubleException {
        out.println(msg);
    }

    public boolean isAlive() {
        try {
            if (communicate(pingString).equals(pingString)) return true;
            else socket.close();
        } catch (ConnectionTroubleException | IOException ex) {
            System.out.println("unable to close connection");
        }
        return false;
    }

    @Override
    public void run() {
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
