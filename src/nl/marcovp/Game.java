package nl.marcovp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game implements Runnable {

    private Socket socket;
    private Players players;

    Game(Socket socket, Players players) {
        this.socket = socket;
        this.players = players;
    }

    @Override
    public void run() {
        System.out.println("Connected: " + socket);
        try {

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String name = "";

            out.flush();

            // Welcome
            out.println("Welcome!");
            out.println("--------");

            // Start the game
            out.println("Type START to start the game...");

            if (in.hasNextLine()) {

                String firstCommand = in.nextLine().toUpperCase();

                if (firstCommand.equals("START")) {

                    // Instructions
                    out.println();
                    out.println("What's your name? ");

                    // Register name
                    if (in.hasNextLine()) {
                        name = in.nextLine();
                    }

                    // Welcome
                    out.println();
                    out.println("Welcome " + name);
                    out.println("Type HELP at any moment to get instructions, type EXIT to quit");
                    out.println();

                    // Put player in list
                    this.players.connect(socket, name);

                    // !!! Move this logic to game class !!! //
                    while (in.hasNextLine()) {

                        String command = in.nextLine().toUpperCase();

                        switch (command) {
                            case "EXIT":
                                socket.close();
                                this.players.close(socket);
                                return;
                            case "LIST":
                                for (Object key : this.players.get().keySet()) {
                                    out.println(key + " - " + this.players.get().get(key));
                                }
                                break;
                            case "HELP":
                                out.println("implement instructions");
                                break;
                            default:
                                out.println("Command not recognised, type HELP for instructions");
                                break;
                        }
                    }
                } else {
                    out.println("This command was not recognised, disconnecting");
                }
            }

        } catch (Exception e) {
            System.out.println("Error:" + socket);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // Catch
            }
            System.out.println("Closed: " + socket);
        }
    }

}
