package nl.marcovp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

    public static void main(String[] args) {

        try (ServerSocket listener = new ServerSocket(8081)) {

            // Alive
            System.out.println("The server is running...");

            // Player List
            Players players = new Players();

            // New Service
            ExecutorService pool = Executors.newFixedThreadPool(20);

            // Accept new Games
            while (true) {
                pool.execute(new Game(listener.accept(), players));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
