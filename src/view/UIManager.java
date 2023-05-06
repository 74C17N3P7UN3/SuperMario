package view;

import control.GameEngine;
import control.GameStatus;
import model.GameObject;
import utils.ImageImporter;

import javax.swing.*;
import java.awt.*;


public class UIManager extends JPanel {
    GameEngine engine;
    ImageLoader loader;

    public UIManager(GameEngine engine, int height, int width) {
        this.engine = engine;
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

        loader = engine.getImageLoader();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g.create();
        // TODO: We need to add the game status if we're going to have some menus
        
        if(engine.getGameStatus() == GameStatus.GAME_OVER) {
        	engine.drawDeadScreen(g2D);
        	engine.getSoundManager().pauseTheme();
        }else {
        	Point camLocation = engine.getCameraPosition();
            g2D.translate(-camLocation.getX(), -camLocation.getY());
            engine.drawMap(g2D);
            g2D.translate(camLocation.getX(), camLocation.getY());
            
            //Render of the time
            g2D.setFont(new Font("Comforta", Font.PLAIN, 28));
            g2D.setColor(Color.WHITE);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.drawString(String.valueOf(engine.getTime()), GameEngine.WIDTH - 100, 55);
            
            //Render of the coins
            g2D.setFont(new Font("Comforta", Font.PLAIN, 28));
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.drawImage(loader.getImage(ImageImporter.loadImage("sprite"), 0, 4, 48, 48), 50 , 30,38,38,null);
            g2D.drawString(String.valueOf(engine.getCoins()), 100, 55); 
        }

        g2D.dispose();
    }
}
