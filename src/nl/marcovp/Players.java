package nl.marcovp;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Players {

    Map<Socket, String> players;

    public Players() {
        this.players = new HashMap<>();
    }

    public void connect(Socket s, String name) {
        this.players.put(s, name);
    }

    public void close(Socket s) {
        this.players.remove(s);
    }

    public Map get() {
        return this.players;
    }
}
