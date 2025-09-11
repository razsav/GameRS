import java.awt.*;

public class Farmer {
    public static final int SIZE = 60;
    private int x, y;
    private ScenePanel scenePanel;
    private boolean alive;

    public Farmer(ScenePanel scenePanel) {
        this.x=scenePanel.getWidth() / 2;
        this.y=scenePanel.getHeight() / 2;
        this.alive = true;
        this.scenePanel = scenePanel;
    }

    public void paint (Graphics graphics) {
        graphics.setColor(Color.BLUE);
        graphics.fillOval(this.x, this.y, SIZE/2, SIZE);
    }


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
