package net;

import model.hero.Mario;

import java.io.Serializable;

/**
 * This is the packet containing information
 * about the local Mario that is going to be
 * sent to the other player.
 *
 * @version 1.0.0
 */
public class Packet implements Serializable {
    public record PacketForm(boolean isSuper, boolean isFire, boolean isBabyStar, boolean isStar) implements Serializable {}

    public final double x, y;
    public final double velX, velY;
    public final boolean falling, jumping;

    public final PacketForm currentForm;
    public final boolean firing, invincible, toRight;

    public Packet(Mario mario) {
        x = mario.getX();
        y = mario.getY();
        velX = mario.getVelX();
        velY = mario.getVelY();
        falling = mario.isFalling();
        jumping = mario.isJumping();

        firing = mario.isFiring();
        invincible = mario.isInvincible();
        toRight = mario.isToRight();

        currentForm = new PacketForm(mario.isSuper(), mario.isFire(), mario.isBabyStar(), mario.isStar());
    }
}
