import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;


public class ScenePanel extends JPanel {

    private Chicken chicken;
    private Chicken[] chickens;
    private ArrayList<Egg> eggs = new ArrayList<Egg>();
    Random random = new Random();
    int x;
    int y;
    int width;
    int height;
    private boolean stop;
    private Farmer farmer;
    private MovementListener movementListener;
    private int eggsColected=0;
    private int brokenEggs = 0;
    private int eggCollectedDeatenation = 5;




    public ScenePanel(int x, int y, int width, int height, int chickensAmount, MovementListener listener){
        this.setBounds(x, y, width, height);
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setBackground(Color.GREEN);
        this.chickens = new Chicken[chickensAmount];


        for (int i=0; i< this.chickens.length; i++){
            this.chickens[i] = new Chicken(random.nextInt(width-chicken.SIZE/2), random.nextInt(height-chicken.SIZE));
        }


        this.eggs.add(new Egg(random.nextInt(width-Egg.SIZE/2), random.nextInt(height-Egg.SIZE)));
//        for (int i=0; i< this.chickens.length; i++){
//            this.eggs.add(new Egg(random.nextInt(width-Egg.SIZE/2), random.nextInt(height-Egg.SIZE)));
//        }

        this.farmer = new Farmer(this);


        this.stop = false;
        this.movementListener = listener;
        this.mainGameLoop();

    }

    private void mainGameLoop () {
        new Thread (()-> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.setFocusable(true);
            this.requestFocus();

            while (true) {
                for (Chicken chicken : this.chickens) {
                    chicken.move(width, height);
                }

                this.checkCollisions();
                this.checkEggsExpiration();
                repaint();

                // תנועת החוואי
                if (movementListener.isRight()) this.getFarmer().moveRight();
                if (movementListener.isLeft()) this.getFarmer().moveLeft();
                if (movementListener.isUp()) this.getFarmer().moveUp();
                if (movementListener.isDown()) this.getFarmer().moveDown();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }).start();

    }

    private void checkCollisions(){
        for (int i=0; i< eggs.size(); i++){
            Egg egg = eggs.get(i);
            if (egg.getBounds().intersects(getFarmer().getBounds())){
                eggsColected++;
                eggs.remove(i);
                int newX = random.nextInt(Math.max(1, width - Egg.SIZE));
                int newY = random.nextInt(Math.max(1, height - Egg.SIZE));
                Egg newEgg = new Egg(newX, newY);
                eggs.add(newEgg);
                i=0;

            }
        }
    }

    public void paintComponent ( Graphics graphics) {
        super.paintComponent(graphics);
        for (Chicken chicken: this.chickens){ chicken.paint(graphics); }
        for (Egg egg : this.eggs){
            egg.paint(graphics);

            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.PLAIN, 12));
            int secondsLeft = egg.getSecondsLeft();
            graphics.drawString(secondsLeft + "s", egg.getX(), egg.getY() - 5);
        }



        this.farmer.paint(graphics);

        graphics.setColor(Color.WHITE); // בחר צבע מתאים לטקסט
        graphics.setFont(new Font("Arial", Font.BOLD, 20)); // הגדר גופן וגודל
        String scoreText = "Eggs: " + this.eggsColected + "/"+ eggCollectedDeatenation; // צור את המחרוזת
        graphics.drawString(scoreText, 10, 20); // צייר את הטקסט במיקום (x, y) מסוים
        graphics.drawString("Broken Eggs: " + brokenEggs + "/5", 10, 50); // מופיע מתחת לסופר

    }

    public Chicken getChicken(){
        return chicken;
    }

    public Farmer getFarmer () {
        return this.farmer;
    }

    public int getEggsColected (){
        return this.eggsColected;
    }

    private void checkEggsExpiration() {
        for (int i = 0; i < eggs.size(); i++) {
            Egg egg = eggs.get(i);
            if (egg.isExpired()) {
                brokenEggs++;
                eggs.remove(i);

                // יצירת ביצה חדשה
                int newX = random.nextInt(Math.max(1, width - Egg.SIZE));
                int newY = random.nextInt(Math.max(1, height - Egg.SIZE));
                eggs.add(new Egg(newX, newY));

                i--; // כדי לא לפספס בדיקה
            }
        }
    }

}

