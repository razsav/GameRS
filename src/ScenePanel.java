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

    private Farmer farmer;
    private MovementListener movementListener;
    private int eggsColected=0;
    private int brokenEggs = 0;
    private int brokenEggsThreshold=5;
    private int eggCollectedDeatenation = 5;
    private boolean play= false;
    private boolean stop = false;
    private boolean puse = false;
    private MenuPanel menuPanel;




    public ScenePanel(int x, int y, int width, int height, MovementListener listener, boolean play, MenuPanel menuPanel){
        this.menuPanel = menuPanel;
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

    }
    public void puseGame(){
        this.puse = true;
    }

    public void continueGame(){
        this.puse = false;
    }

    public void stopGame(){
        this.stop = true;
    }

    public void startNewGame (){
        this.play = true;
        this.stop = false;
        this.puse= false;

        this.farmer= new Farmer(this);
        this.farmer.Alive();

        this.chickens = new Chicken[5];
        for (int i = 0; i < this.chickens.length; i++) {
            int maxX = Math.max(1, width - Chicken.SIZE);
            int maxY = Math.max(1, height - Chicken.SIZE);
            this.chickens[i] = new Chicken(random.nextInt(maxX), random.nextInt(maxY));
        }

        int maxBlackX = Math.max(1, width - BlackChicken.SIZE);
        int maxBlackY = Math.max(1, height - BlackChicken.SIZE);
        this.blackChicken = new BlackChicken(random.nextInt(maxBlackX), random.nextInt(maxBlackY));

        this.eggs.clear();
        int maxEggX = Math.max(1, width - Egg.SIZE);
        int maxEggY = Math.max(1, height - Egg.SIZE);
        this.eggs.add(new Egg(random.nextInt(maxEggX), random.nextInt(maxEggY)));

        this.eggsColected = 0;
        this.brokenEggs = 0;

        mainGameLoop();
        repaint();

        this.requestFocusInWindow();

    }

    public void setPlay () {
        if (!play){
            startNewGame();
        }

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


            while (play && farmer.isAlive() && !stop) {

                if (!puse){
                    for (Chicken chicken : this.chickens) {
                        chicken.move(width, height);
                    }
                    this.blackChicken.move(width, height);

                    this.checkBrokenEggsThreshold();
                    this.checkCollisions();
                    this.checkEggsExpiration();


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
                }

                repaint();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }


        });
        gameThread.start();

    }

    public void checkBrokenEggsThreshold(){
        if (this.brokenEggs >= this.brokenEggsThreshold){
            this.farmer.die();
        }
    }


    private void checkCollisions(){
        for (int i=0; i< eggs.size(); i++){
            Egg egg = eggs.get(i);
            if (egg.getBounds().intersects(getFarmer().getBounds())){
                eggsColected++;
                SwingUtilities.invokeLater(() -> menuPanel.updateCounterEggs(eggsColected, eggCollectedDeatenation));
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
        super.paintComponent(graphics);

        if (puse && eggsColected != eggCollectedDeatenation){
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Arial", Font.BOLD, 30));
            graphics.drawString("Game puse", width/8+120, height/2-30);
            graphics.drawString("press 'Continue' to continue the game", width/8, height/2);
            return;
        }

        if (!play){
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.PLAIN, 20));
            graphics.drawString( "Press 'Play' to start", width/4, height/4);
            return;

        }

        if (farmer.isAlive() && eggsColected != eggCollectedDeatenation) {
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

//            graphics.setColor(Color.WHITE); // בחר צבע מתאים לטקסט
//            graphics.setFont(new Font("Arial", Font.BOLD, 20)); // הגדר גופן וגודל
//            String scoreText = "Eggs: " + this.eggsColected + "/"+ eggCollectedDeatenation; // צור את המחרוזת
//            graphics.drawString(scoreText, 10, 20); // צייר את הטקסט במיקום (x, y) מסוים
//            graphics.drawString("Broken Eggs: " + brokenEggs + "/" + brokenEggsThreshold, 10, 50); // מופיע מתחת לסופר

        } else if (!farmer.isAlive() && eggsColected != eggCollectedDeatenation) {
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Arial", Font.BOLD, 40));
            graphics.drawString("Game Over", width/4, height/2);
        } else {
            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font("Arial", Font.BOLD, 40));
            graphics.drawString("You WIN", width/4, height/2);
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

    public int getEggCollectedDeatenation(){
        return eggCollectedDeatenation;
    }


    private void checkEggsExpiration() {
        for (int i = 0; i < eggs.size(); i++) {
            Egg egg = eggs.get(i);
            if (egg.isExpired()) {
                brokenEggs++;
                SwingUtilities.invokeLater(() -> menuPanel.updateCounterBrokenEggs(this.brokenEggs, this.brokenEggsThreshold));
                eggs.remove(i);

                // יצירת ביצה חדשה
                int newX = random.nextInt(width - Egg.SIZE + 1);
                int newY = random.nextInt(height - Egg.SIZE + 1);
                eggs.add(new Egg(newX, newY));

                i--; // כדי לא לפספס בדיקה
            }
        }
    }

}

