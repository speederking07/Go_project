package Server;

import Server.Exceprions.ConnectionTroubleException;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private volatile static Queue instance;
    private HumanPlayer waitFor9, waitFor13, waitFor19;
    //private List<Thread> games;

    private Queue(){
        waitFor9 = null;
        waitFor13 = null;
        waitFor19 = null;
        //games = new ArrayList<Game>();
    }

    private void createGame(Player p1, Player p2, int size){
        try {
            p1.startGame(Color.Black);
            p2.startGame(Color.White);
        } catch (ConnectionTroubleException ex){
            p1.endGame("ConnectionTrouble", 0, 0);
            p2.endGame("ConnectionTrouble", 0, 0);
        }
        Thread t = new Thread(new Game(p1,p2,size));
        t.start();
        //games.add(new Game(p1,p2,size));
    }

    public void makeGameWithPlayer(HumanPlayer hp, int size){
        if (size == 9){
            if(waitFor9 != null){
                synchronized (this){
                    if(waitFor9 != null){
                        createGame(waitFor9, hp, size);
                    }
                    else waitFor9 = hp;
                }
            }
        }
        else if (size == 13){
            if(waitFor13 != null){
                synchronized (this){
                    if(waitFor13 != null){
                        createGame(waitFor13, hp, size);
                    }
                    else waitFor13 = hp;
                }
            }
        }
        else if (size == 19){
            if(waitFor19 != null){
                synchronized (this){
                    if(waitFor19 != null){
                        createGame(waitFor19, hp, size);
                    }
                    else waitFor19 = hp;
                }
            }
        }
    }

    public void makeGameWithBot(HumanPlayer hp, int size) {
        //TODO: Bot
    }

    public static Queue getInstance(){
        if (instance == null){
            synchronized (Queue.class){
                if(instance == null) instance = new Queue();
            }
        }
        return instance;
    }
}
