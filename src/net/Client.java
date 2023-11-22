package net;

import control.GameEngine;
import model.hero.Mario;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The client class, responsible for
 * communicating with the connected TCP server.
 *
 * @version 1.0.0
 */
public class Client implements Runnable {
    private GameEngine engine;
    private boolean interrupt;

    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Client(GameEngine engine, String serverIp) {
        try {
            this.engine = engine;
            interrupt = false;

            System.out.println("Client connecting to server...");
            connection = new Socket(serverIp, 6996);
            System.out.println("Client connected to server!");

            input = new ObjectInputStream(connection.getInputStream());
            output = new ObjectOutputStream(connection.getOutputStream());

            engine.createMap("map-01", true);
        } catch (Exception ignored) {}
    }

    /**
     * Sets a flag to interrupt the thread.
     */
    public void interrupt() {
        interrupt = true;
    }

    @Override
    public void run() {
        while (!interrupt) {
            try {
                System.out.println("Client waiting for object from server...");
                Mario netMario = (Mario) input.readObject();
                System.out.println("Client received object from server!");
                if (netMario.getX() >= ((48 * 198) - 20) && netMario.getX() < 10992) break;

                engine.getMapManager().getMap().setNetMario(netMario);
            } catch (Exception ignored) {}
        }
    }

    /**
     * Sends an update to the host server
     * with the current Mario coordinates.
     *
     * @param mario The Mario object.
     */
    public void sendUpdate(Mario mario) {
        try {
            System.out.println("Client sending update to server...");
            output.writeObject(mario);
            output.flush();
            System.out.println("Client sent update to server!");
        } catch (Exception ignored) {}
    }
}
