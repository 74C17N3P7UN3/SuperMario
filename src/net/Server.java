package net;

import control.GameEngine;
import control.GameStatus;
import model.hero.Mario;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The server class, responsible for hosting
 * the multiplayer game and communicating with
 * the connected TCP client.
 *
 * @version 1.0.0
 */
public class Server implements Runnable {
    private GameEngine engine;
    private boolean interrupt;

    private ServerSocket server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Server(GameEngine engine) {
        try {
            this.engine = engine;
            interrupt = false;

            System.out.println("Server waiting for client...");
            server = new ServerSocket(6996);
            connection = server.accept();
            System.out.println("Server accepted connection from client!");

            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());

            engine.createMap("map-01", true);
        } catch (Exception ignored) {}
    }

    @Override
    public void run() {
        while (!interrupt) {
            try {
                System.out.println("Server waiting for object from client...");
                Mario netMario = (Mario) input.readObject();
                System.out.println("Server received object from client!");
                if (netMario.getX() >= ((48 * 198) - 20) && netMario.getX() < 10992) break;

                engine.getMapManager().getMap().setNetMario(netMario);
            } catch (Exception ignored) {}
        }
    }

    /**
     * Sets a flag to interrupt the thread.
     */
    public void interrupt() {
        interrupt = true;
    }

    /**
     * Sends an update to the connected client
     * with the current Mario coordinates.
     *
     * @param mario The Mario object.
     */
    public void sendUpdate(Mario mario) {
        try {
            System.out.println("Server sending update to client...");
            output.writeObject(mario);
            output.flush();
            System.out.println("Server sent update to client!");
        } catch (Exception ignored) {}
    }
}
