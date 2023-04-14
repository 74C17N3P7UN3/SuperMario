package view;

import control.GameEngine;

import javax.swing.*;
import java.awt.*;


public class UIManager extends JPanel {
    GameEngine engine;

    public UIManager(GameEngine engine, int height, int width) {
        this.engine = engine;
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

        ImageLoader loader = engine.getImageLoader();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g.create();
        // TODO: We need to add the game status if we're going to have some menus

        Point camLocation = engine.getCameraPosition();
        g2D.translate(-camLocation.getX(), -camLocation.getY());
        engine.drawMap(g2D);
        g2D.translate(camLocation.getX(), camLocation.getY());

        g2D.dispose();
    }
}
