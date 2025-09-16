/**
 * Farmer - Represents the player character in the game.
 * The Farmer can move in four directions, collect eggs,
 * and loses the game if colliding with black chickens or
 * breaking too many eggs.
 */

import java.awt.*;

public class Farmer {

    /** Default farmer size used for drawing. */
    public static final int SIZE = 30;

    /** Bounding box width (for collisions). */
    public static final int BOUNDING_WIDTH = SIZE;

    /** Bounding box height (for collisions, includes head, body, legs, hat). */
    public static final int BOUNDING_HEIGHT = 50;
    private int x, y;
    private ScenePanel scenePanel;
    private boolean alive;
    private int speed = 5;

    /**
     * Creates a new Farmer instance positioned at the center of the ScenePanel.
     * @param scenePanel the game scene this farmer belongs to
     */
    public Farmer(ScenePanel scenePanel) {
        this.x=scenePanel.getWidth() / 2;
        this.y=scenePanel.getHeight() / 2;
        this.alive = true;
        this.scenePanel = scenePanel;
    }

    /**
     * Draws the farmer (head, body, arms, legs, hat).
     * @param g the Graphics context to draw on
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // ----- ראש -----
        g2.setColor(Color.PINK);
        g2.fillOval(this.x+5, this.y, SIZE/2, SIZE/2);

        // ----- גוף -----
        g2.setColor(Color.BLUE);
        g2.fillRect(this.x + SIZE/4 + SIZE/8, this.y + SIZE/2, SIZE/4, SIZE/2);

        // ----- ידיים -----
        g2.setColor(Color.BLUE);
        // יד שמאל
        g2.fillRect(this.x-1, this.y + SIZE/2, 10, SIZE/5);
        // יד ימין
        g2.fillRect(this.x + SIZE/2 +3 , this.y + SIZE/2, 10, SIZE/5);

        // ----- רגליים -----
        g2.setColor(Color.BLACK);
        // רגל שמאל
        g2.fillRect(this.x + SIZE/8 + SIZE/16, this.y + SIZE, 5, SIZE/3);
        // רגל ימין
        g2.fillRect(this.x + SIZE/8 + 15, this.y + SIZE, 5, SIZE/3);

        // ----- כובע -----
        g2.setColor(Color.DARK_GRAY);
        // שוליים
        g2.fillRect(this.x, this.y - 3, SIZE-5, 5);
        // מרכז הכובע
        g2.fillRect(this.x+8, this.y - 9, SIZE/2 - 5, 10);
    }

    /** Marks the farmer as dead (used when colliding with black chickens or losing). */
    public void die () {
        this.alive = false;
    }

    /** Marks the farmer as alive (used when starting a new game). */
    public void Alive () {this.alive = true;}

    /** @return true if the farmer is alive, false otherwise */
    public boolean isAlive() {
        return alive;
    }

    /** Moves the farmer right, limited by scene boundaries. */
    public void moveRight() {
        this.x = Math.min(this.x + speed, scenePanel.getWidth() - BOUNDING_WIDTH);
    }

    /** Moves the farmer left, limited by scene boundaries. */
    public void moveLeft() {
        this.x = Math.max(this.x - speed, 0);
    }

    /** Moves the farmer up, limited by scene boundaries. */
    public void moveUp() {
        this.y = Math.max(this.y - speed, 0);
    }

    /** Moves the farmer down, limited by scene boundaries. */
    public void moveDown() {
        this.y = Math.min(this.y + speed, scenePanel.getHeight() - BOUNDING_HEIGHT);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    /**
     * @return bounding rectangle used for collision detection with eggs/chickens
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, BOUNDING_WIDTH, BOUNDING_HEIGHT);
    }


}
