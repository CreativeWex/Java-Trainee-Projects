package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;

public class Main {
    public static void main(String[] args) {
        if(args.length == 1 && args[0].matches("--port=\\d+")) {
            int port = Integer.parseInt(args[0].split("=")[1]);
            Server server = new Server(port);
            server.start();
        }
    }
}
