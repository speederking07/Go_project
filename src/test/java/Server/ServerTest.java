package Server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void main() {
        Thread t = new Thread(() -> {
            new Server(4122);
        });
        t.start();
        try {
            new Socket("localhost", 4122);
            new Socket("localhost", 4122);
            new Socket("localhost", 4122);
        } catch (IOException e) {
            Assertions.fail();
        }
        t.stop();
    }
}