package net.local;

import control.GameEngine;
import model.hero.Mario;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The client class, responsible for
 * communicating with the connected TCP server.
 *
 * @version 1.1.0
 */
public class Client implements Runnable {
    private GameEngine engine;
    private boolean interrupt;
    private Thread thread;

    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Client(GameEngine engine, String serverIp) {
        try {
            this.engine = engine;
            interrupt = false;

            connection = new Socket(serverIp, 6996);

            input = new ObjectInputStream(connection.getInputStream());
            output = new ObjectOutputStream(connection.getOutputStream());

            engine.createMap("map-01", true);
            start();
        } catch (Exception ignored) {}
    }

    /**
     * The initializer method of the client thread,
     * which calls the {@link Thread#start()} method.
     */
    private void start() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * The implementation of the {@link Runnable} interface.
     */
    @Override
    public void run() {
        while (!interrupt) {
            try {
                Packet packet = (Packet) input.readObject();
                if (packet.x >= ((48 * 198) - 20) && packet.x < 10992) break;

                engine.getMapManager().getMap().getNetMario().updateFromPacket(packet);
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
     * Sends an update to the host server
     * with the current Mario object wrapped
     * in a special serializable packet.
     *
     * @param mario The Mario object.
     */
    public void sendUpdate(Mario mario) {
        try {
            output.writeObject(new Packet(mario));
            output.flush();
        } catch (Exception ignored) {}
    }

    /* ---------- Getters / Setters ---------- */

    public boolean isInterrupted() {
        return interrupt;
    }
}
