import java.awt.*;

public class Egg {


    public static final int SIZE = 10;
    private int x, y;

    private long creationTime;
    private long lifeTime = 30_000;


    public Egg (int x, int y, int width, int height){
        this.x = Math.max(0, Math.min(x, width - SIZE/2));
        this.y = Math.max(0, Math.min(y, height - SIZE));
        this.creationTime = System.currentTimeMillis();
    }

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

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - creationTime >= lifeTime;
    }

    public int getSecondsLeft() {
        long elapsed = System.currentTimeMillis() - creationTime;
        long remaining = lifeTime - elapsed;
        return (int) Math.max(0, remaining / 1000); // שניות שנותרו
    }


}
