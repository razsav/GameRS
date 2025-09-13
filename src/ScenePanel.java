import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;


public class ScenePanel extends JPanel {

    private Chicken[] chickens;
    private ArrayList<Egg> eggs = new ArrayList<Egg>();
    private BlackChicken blackChicken;
    private Random random = new Random();
    private int width, height;
    private boolean stop = false;
    private Farmer farmer;
    private MovementListener movementListener;
    private int eggsColected=0;
    private int brokenEggs = 0;
    private int eggCollectedDeatenation = 5;
    private boolean play= false;




    public ScenePanel(int x, int y, int width, int height, MovementListener listener, boolean play){
        this.play=play;
        this.setBounds(x, y, width, height);
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setBackground(Color.GREEN);
        this.chickens = new Chicken[5];
        this.blackChicken = new BlackChicken(random.nextInt(width-blackChicken.SIZE/2), random.nextInt(height-blackChicken.SIZE));

        if (play ){
            startNewGame();
        }
//        this.farmer = new Farmer(this);

//        if (this.farmer.isAlive() && play == true){
//            for (int i=0; i< this.chickens.length; i++){
//                this.chickens[i] = new Chicken(random.nextInt(width-chicken.SIZE/2), random.nextInt(height-chicken.SIZE));
//            }
//
//
//            this.eggs.add(new Egg(random.nextInt(width-Egg.SIZE/2), random.nextInt(height-Egg.SIZE)));
////        for (int i=0; i< this.chickens.length; i++){
////            this.eggs.add(new Egg(random.nextInt(width-Egg.SIZE/2), random.nextInt(height-Egg.SIZE)));
////        }
//
//            this.farmer = new Farmer(this);
//
//
//            this.stop = false;
//            this.movementListener = listener;
//            this.mainGameLoop();
//        }
    }

    public void startNewGame (){
        this.play = true;
        this.stop = false;

        this.farmer= new Farmer(this);
        this.farmer.Alive();

        this.chickens = new Chicken[5];
        for (int i = 0; i < this.chickens.length; i++) {
            this.chickens[i] = new Chicken(
                    random.nextInt(width - Chicken.SIZE),
                    random.nextInt(height - Chicken.SIZE)
            );
        }

        this.blackChicken = new BlackChicken(
                random.nextInt(width - BlackChicken.SIZE),
                random.nextInt(height - BlackChicken.SIZE)
        );

        this.eggs.clear();
        this.eggs.add(new Egg(
                random.nextInt(width - Egg.SIZE),
                random.nextInt(height - Egg.SIZE)
        ));

        this.eggsColected = 0;
        this.brokenEggs = 0;

        mainGameLoop();
        repaint();

        this.requestFocusInWindow();

    }

    public void setPlay () {
        startNewGame();
    }

    private Thread gameThread;

    private void mainGameLoop () {

        if (gameThread != null && gameThread.isAlive()) {
            return; // כבר רץ, לא להפעיל שוב
        }

        gameThread = new Thread (()-> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            while (play && farmer.isAlive()) {
                for (Chicken chicken : this.chickens) {
                    chicken.move(width, height);
                }
                this.blackChicken.move(width, height);

                this.checkCollisions();
                this.checkEggsExpiration();
                repaint();

                // תנועת החוואי
                if (movementListener.isRight()) {
                    this.getFarmer().moveRight();
                }
                if (movementListener.isLeft()) {
                    this.getFarmer().moveLeft();
                }
                if (movementListener.isUp()) {
                    this.getFarmer().moveUp();
                }
                if (movementListener.isDown()) {
                    this.getFarmer().moveDown();
                }


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }


        });
        gameThread.start();

    }


    private void checkCollisions(){
        for (int i=0; i< eggs.size(); i++){
            Egg egg = eggs.get(i);
            if (egg.getBounds().intersects(getFarmer().getBounds())){
                eggsColected++;
                eggs.remove(i);
                int newX = random.nextInt(Math.max(Egg.SIZE*16, width - Egg.SIZE*8));
                int newY = random.nextInt(Math.max(Egg.SIZE*16, height - Egg.SIZE*8));
                Egg newEgg = new Egg(newX, newY);
                eggs.add(newEgg);
                i=0;

            }
        }
        if (blackChicken.getBounds().intersects(getFarmer().getBounds())){
            this.farmer.die();
        }
    }

    public void paintComponent ( Graphics graphics) {

        if (!play){
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.PLAIN, 20));
            graphics.drawString( "Press 'Play' to start", width/4, height/4);
            return;

        } else if (farmer.isAlive()) {
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
            this.blackChicken.paint(graphics);

            graphics.setColor(Color.WHITE); // בחר צבע מתאים לטקסט
            graphics.setFont(new Font("Arial", Font.BOLD, 20)); // הגדר גופן וגודל
            String scoreText = "Eggs: " + this.eggsColected + "/"+ eggCollectedDeatenation; // צור את המחרוזת
            graphics.drawString(scoreText, 10, 20); // צייר את הטקסט במיקום (x, y) מסוים
            graphics.drawString("Broken Eggs: " + brokenEggs + "/5", 10, 50); // מופיע מתחת לסופר

        } else{
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Arial", Font.BOLD, 40));
            graphics.drawString("Game Over", width/4, height/2);


        }

    }

    public Farmer getFarmer () {
        return this.farmer;
    }

    public int getEggsColected (){
        return this.eggsColected;
    }

    public int getHeight(){
        return height;
    }

    public void setMovementListener(MovementListener listener) {
        this.movementListener = listener;
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

