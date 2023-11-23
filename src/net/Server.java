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

            server = new ServerSocket(6996);
            connection = server.accept();

            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());

            engine.createMap("map-01", true);
        } catch (Exception ignored) {}
    }

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
     * Sends an update to the connected client
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
}
