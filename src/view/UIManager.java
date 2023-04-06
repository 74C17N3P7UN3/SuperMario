package view;

import control.GameEngine;

import javax.swing.*;
import java.awt.*;


public class UIManager extends JPanel {

    GameEngine engine;

    public UIManager(GameEngine e, int width, int height) {
        this.engine = e;
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

        ImageLoader loader = engine.getImageLoader();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        // TODO: We need to add the game status if we're going to have some menus

        Point camLocation = engine.getCameraPosition();
        g2.translate(-camLocation.x, -camLocation.y);
        // FIXME: engine.drawMap(g2);
        g2.translate(camLocation.x, camLocation.y);

        g2.dispose();
    }
}
