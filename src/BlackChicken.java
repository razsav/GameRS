import java.awt.*;
import java.util.Random;

public class BlackChicken {
    public static final int SIZE = 30;
    public static final int BOUNDING_WIDTH = SIZE / 2;  // 15
    public static final int BOUNDING_HEIGHT = SIZE;     // 30
    private int x, y;
    private int direction = -1;
    private Random random = new Random();



    public BlackChicken(int width, int height){

        int maxX = Math.max(1, width - SIZE/2);
        int maxY = Math.max(1, height - SIZE);

        this.x = random.nextInt(maxX);
        this.y = random.nextInt(maxY);
        this.direction = random.nextInt(8);
    }

    public void paint (Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillOval(this.x, this.y, SIZE/2, SIZE);
        drawTriangle(graphics, this.x+5, this.y-5,SIZE/6, Color.RED, Color.RED);
        drawTriangle(graphics, this.x+10, this.y-5,SIZE/6, Color.RED, Color.RED);
        drawTriangle(graphics, this.x+15, this.y-5,SIZE/6, Color.RED, Color.RED);
        drawTriangle(graphics, this.x-2, this.y+6,SIZE/6, Color.YELLOW, Color.YELLOW);
    }

    public void drawTriangle(Graphics g, int x, int y, int size, Color color, Color color2) {
        Graphics2D g2 = (Graphics2D) g;

        // קואורדינטות של שלושת הקודקודים
        int[] xPoints = {
                x,               // הקודקוד העליון
                x - size / 2,    // שמאל למטה
                x + size / 2     // ימין למטה
        };
        int[] yPoints = {
                y,               // הקודקוד העליון
                y + size,        // שמאל למטה
                y + size         // ימין למטה
        };

        g2.setColor(color);
        g2.fillPolygon(xPoints, yPoints, 3); // צובע את המשולש

        g2.setColor(color2);
        g2.drawPolygon(xPoints, yPoints, 3); // מצייר קווי מתאר
    }


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

    public boolean checkIfCanMove (int width, int height){
        if(this.y > 0 && this.x > 0 && this.x < width && this.y < height){
            return true;
        }
        return false;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, BOUNDING_WIDTH, BOUNDING_HEIGHT);
    }

}
