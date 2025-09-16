/**
 * Egg - Represents an egg dropped by a chicken in the game.
 * Eggs expire after a limited lifetime and can be collected
 * by the farmer for points.
 */

import java.awt.*;

public class Egg {

    /** Default egg size in pixels. */
    public static final int SIZE = 10;
    private int x, y;

    private long creationTime;
    private long lifeTime = 30_000;// 30 seconds

    /**
     * Creates a new Egg at a given position, adjusted within scene bounds.
     * @param x initial x position
     * @param y initial y position
     * @param width scene width
     * @param height scene height
     * @param marginTop top margin to keep eggs from spawning too high
     */
    public Egg(int x, int y, int width, int height, int marginTop){
        this.x = Math.max(0, Math.min(x, width - SIZE/2));
        this.y = Math.max(marginTop, Math.min(y, height - SIZE));
        this.creationTime = System.currentTimeMillis();
    }

    /**
     * Draws the egg on screen.
     * @param graphics the Graphics context to draw on
     */
    public void paint (Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillOval(this.x, this.y, SIZE/2, SIZE);
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    /** @return bounding rectangle used for collision detection */
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    /** @return true if the egg's lifetime has expired */
    public boolean isExpired() {
        return System.currentTimeMillis() - creationTime >= lifeTime;
    }

    /** @return seconds left before the egg expires */
    public int getSecondsLeft() {
        long elapsed = System.currentTimeMillis() - creationTime;
        long remaining = lifeTime - elapsed;
        return (int) Math.max(0, remaining / 1000); // שניות שנותרו
    }


}
