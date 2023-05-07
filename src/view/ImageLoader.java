package view;

import utils.ImageImporter;

import java.awt.image.BufferedImage;

// FIXME: Calza Reminder
/**
 * Provides basic methods for loading images
 * and getting specific frames or parts of
 * the animated sprite texture from a png.
 *
 * @author Bartolomuwu
 * @version 0.1.0
 */
public class ImageLoader {
    private BufferedImage marioForms;
    private BufferedImage brickAnimation;

    public ImageLoader() {
        marioForms = ImageImporter.loadImage("mario-forms");
        brickAnimation = ImageImporter.loadImage("brick-animation");
    }

    public static BufferedImage getImage(BufferedImage image, int x, int y, int w, int h) {
        return image.getSubimage(x * 48, y * 48, w, h);
    }

    public BufferedImage[] getLeftFrames(int marioForm) {
    	int dim = 5;
    	if(marioForm == 3 || marioForm == 4)
    		dim = 20;

        BufferedImage[] leftFrames = new BufferedImage[dim];
        int col = 1;
        int width = 52, height = 48;

        if (marioForm == 1) {
            col = 4;
            width = 48;
            height = 96;
        } else if (marioForm == 2) {
            col = 7;
            width = 48;
            height = 96;
        } else if (marioForm == 4) {
        	col = 4;
        	width = 48;
        	height = 96;
        }

        for (int i = 0; i < 5; i++)
            leftFrames[i] = marioForms.getSubimage((col - 1) * 48, i * height, width, height);
        if(marioForm == 3 || marioForm == 4) {
        	int count = 5;
        	for(int n = 0; n<3; n++) {
	        	for(int i = 0; i<5; i++) {
	        		leftFrames[count] = marioForms.getSubimage((col+8 + n*6) * 48, i * height, width, height);
	        		count++;
	        	}
        	}
        }

        return leftFrames;
    }

    public BufferedImage[] getRightFrames(int marioForm) {
    	int dim = 5;
    	if(marioForm == 3 || marioForm == 4)
    		dim = 20;

        BufferedImage[] rightFrames = new BufferedImage[dim];
        int col = 2;
        int width = 52, height = 48;

        if (marioForm == 1) {
            col = 5;
            width = 48;
            height = 96;
        } else if (marioForm == 2) {
            col = 8;
            width = 48;
            height = 96;
        } else if (marioForm == 4) {
        	col = 5;
        	width = 48;
        	height = 96;
        }

        for (int i = 0; i < 5; i++)
            rightFrames[i] = marioForms.getSubimage((col - 1) * 48, i * height, width, height);
        if(marioForm == 3 || marioForm == 4) {
        	int count = 5;
        	for(int n = 0; n<3; n++) {
	        	for(int i = 0; i<5; i++) {
	        		rightFrames[count] = marioForms.getSubimage((col+8 + n*6) * 48, i * height, width, height);
	        		count++;
	        	}
        	}
        }
        return rightFrames;
    }

    // FIXME: We need this?
    public BufferedImage[] getBrickFrames() {
        BufferedImage[] frames = new BufferedImage[4];
        for (int i = 0; i < 4; i++)
            frames[i] = brickAnimation.getSubimage(i * 105, 0, 105, 105);
        return frames;
    }
}
