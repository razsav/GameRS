import java.awt.*;

public class Farmer {
    public static final int SIZE = 30;
    private int x, y;
    private ScenePanel scenePanel;
    private boolean alive;

    public Farmer(ScenePanel scenePanel) {
        this.x=scenePanel.getWidth() / 2;
        this.y=scenePanel.getHeight() / 2;
        this.alive = true;
        this.scenePanel = scenePanel;
    }

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


//    public void paint (Graphics graphics) {
//        graphics.setColor(Color.BLUE);
//        graphics.fillOval(this.x, this.y, SIZE/2, SIZE);
//    }


    public void die () {
        this.alive = false;
    }

    public void moveRight () {
        if (this.x < this.scenePanel.getWidth() - SIZE) {
            this.x++;
        }
    }

    public void moveLeft () {
        if (this.x > 0) {
            this.x--;
        }
    }

    public void moveUp () {
        if (this.y > 0) {
            this.y--;
        }
    }

    public void moveDown () {
        this.y++;
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

    public boolean isAlive() {
        return alive;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }


}
