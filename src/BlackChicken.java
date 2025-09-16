/**
 * BlackChicken - Represents a hostile black chicken in the game.
 * Moves randomly and can collide with the Farmer to cause defeat.
 */

import java.awt.*;
import java.util.Random;

public class BlackChicken {

    /** Chicken display size (ellipse height). */
    public static final int SIZE = 30;

    /** Bounding box width for collisions. */
    public static final int BOUNDING_WIDTH = SIZE / 2;

    /** Bounding box height for collisions. */
    public static final int BOUNDING_HEIGHT = SIZE;
    private int x, y;
    private int direction = -1;
    private Random random = new Random();

    /**
     * Constructor - spawns black chicken at random position and direction.
     * @param width Panel width
     * @param height Panel height
     */
    public BlackChicken(int width, int height){

        int maxX = Math.max(1, width - SIZE/2);
        int maxY = Math.max(1, height - SIZE);

        this.x = random.nextInt(maxX);
        this.y = random.nextInt(maxY);
        this.direction = random.nextInt(8);
    }

    /**
     * Draws the black chicken (body, feathers, beak).
     */
    public void paint (Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillOval(this.x, this.y, SIZE/2, SIZE);
        drawTriangle(graphics, this.x+5, this.y-5,SIZE/6, Color.RED, Color.RED);
        drawTriangle(graphics, this.x+10, this.y-5,SIZE/6, Color.RED, Color.RED);
        drawTriangle(graphics, this.x+15, this.y-5,SIZE/6, Color.RED, Color.RED);
        drawTriangle(graphics, this.x-2, this.y+6,SIZE/6, Color.YELLOW, Color.YELLOW);
    }

    /**
     * Helper method to draw a triangle (used for feathers/beak).
     */
    public void drawTriangle(Graphics g, int x, int y, int size, Color color, Color color2) {
        Graphics2D g2 = (Graphics2D) g;

        int[] xPoints = {
                x,
                x - size / 2,
                x + size / 2
        };
        int[] yPoints = {
                y,
                y + size,
                y + size
        };

        g2.setColor(color);
        g2.fillPolygon(xPoints, yPoints, 3);

        g2.setColor(color2);
        g2.drawPolygon(xPoints, yPoints, 3);
    }


    /**
     * Moves the black chicken in its current direction,
     * bounces off panel borders and changes direction randomly.
     */
    public void move(int width, int height) {
        int oldX = this.x;
        int oldY = this.y;

        switch (direction) {
            case 0: // North
                this.y--;
                break;
            case 1: // North-East
                this.y--;
                this.x++;
                break;
            case 2: // East
                this.x++;
                break;
            case 3: // South-East
                this.y++;
                this.x++;
                break;
            case 4: // South
                this.y++;
                break;
            case 5: // South-West
                this.y++;
                this.x--;
                break;
            case 6: // West
                this.x--;
                break;
            case 7: // North-West
                this.y--;
                this.x--;
                break;
        }

        // בדיקות גבולות
        if (this.x < 0) this.x = 0;
        if (this.y < 0) this.y = 0;
        if (this.x > width - BOUNDING_WIDTH) this.x = width - BOUNDING_WIDTH;
        if (this.y > height - BOUNDING_HEIGHT) this.y = height - BOUNDING_HEIGHT;


        // אם נתקעה בקיר, שנה כיוון
        if (this.x == 0 || this.y == 0 || this.x == width - SIZE || this.y == height - SIZE) {
            this.direction = new Random().nextInt(8);
        }
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns bounding rectangle used for collision detection.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, BOUNDING_WIDTH, BOUNDING_HEIGHT);
    }

}
