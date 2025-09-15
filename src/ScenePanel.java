/**
 * ScenePanel - Main game panel where the action happens.
 * Handles game objects (farmer, chickens, black chickens, eggs),
 * game state (play, pause, stop, win/lose),
 * rendering, collisions, and the main game loop.
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class ScenePanel extends JPanel {

    private Chicken[] chickens;
    private ArrayList<Egg> eggs = new ArrayList<Egg>();
    private ArrayList<BlackChicken> blackChickens = new ArrayList<BlackChicken>();
    private int numberOfBlackChickens=1;
    private Random random = new Random();
    private int width, height;
    private static final int MARGIN_TOP = 15; // מספיק מקום לטקסט מעל הביצה
    private static final int MARGIN_BOTTOM = Egg.SIZE * 2;
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


    /**
     * Constructor - initializes the panel and starts a new game if play==true.
     * @param x X position of panel
     * @param y Y position of panel
     * @param width Panel width
     * @param height Panel height
     * @param listener Movement listener (keyboard control)
     * @param play Start immediately if true
     * @param menuPanel Reference to MenuPanel for updating counters
     */
    public ScenePanel(int x, int y, int width, int height, MovementListener listener, boolean play, MenuPanel menuPanel){
        this.menuPanel = menuPanel;
        this.play=play;
        this.setBounds(x, y, width, height);
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setBackground(Color.GREEN);
        this.chickens = new Chicken[5];

        if (play ){
            startNewGame();
        }

    }

    /**
     * Pauses the game (freezes all actions).
     */
    public void puseGame(){
        this.puse = true;
    }

    /**
     * Resumes the game after pause.
     */
    public void continueGame(){
        this.puse = false;
    }

    /**
     * Stops the game completely (end state).
     */
    public void stopGame(){
        this.stop = true;
    }

    /**
     * Starts a new game session:
     * - resets farmer, chickens, black chickens, and eggs
     * - clears counters
     * - starts main game loop
     */
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

        this.blackChickens.clear();
        int maxBlackX = Math.max(1, width - BlackChicken.SIZE);
        int maxBlackY = Math.max(1, height - BlackChicken.SIZE);
        for(int i=0; i< numberOfBlackChickens; i++){
            this.blackChickens.add(new BlackChicken(random.nextInt(maxBlackX), random.nextInt(maxBlackY)));
        }

        this.eggs.clear();
        int newX = random.nextInt(width - Egg.SIZE + 1);
        int newY = random.nextInt(MARGIN_TOP, height - Egg.SIZE - MARGIN_BOTTOM);
        eggs.add(new Egg(newX, newY, width, height, this.MARGIN_TOP));
        if (!eggs.isEmpty()) {
            System.out.println(
                    "Last Egg position: x=" + eggs.get(eggs.size() - 1).getX() +
                            ", y=" + eggs.get(eggs.size() - 1).getY()
            );
        }

        this.eggsColected = 0;
        this.brokenEggs = 0;
        SwingUtilities.invokeLater(() -> menuPanel.updateCounterBrokenEggs(this.brokenEggs, this.brokenEggsThreshold));
        SwingUtilities.invokeLater(() -> menuPanel.updateCounterEggs(eggsColected, eggCollectedDeatenation));

        mainGameLoop();
        repaint();

        this.requestFocusInWindow();

    }

    /**
     * Ensures play is active; starts a new game if needed.
     */
    public void setPlay () {
        if (!play){
            startNewGame();
        }

    }

    private Thread gameThread;

    /**
     * Main game loop running in a separate thread.
     * Handles movement, collisions, and repainting until game ends.
     */
    public void mainGameLoop () {

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

                    for (BlackChicken blackChicken : this.blackChickens) {
                        blackChicken.move(width, height);
                    }

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

    /**
     * Checks if broken eggs exceeded threshold and kills farmer if so.
     */
    public void checkBrokenEggsThreshold(){
        if (this.brokenEggs >= this.brokenEggsThreshold){
            this.farmer.die();
        }
    }

    /**
     * Detects collisions:
     * - Farmer with eggs (collects egg)
     * - Farmer with black chickens (game over)
     */
    public void checkCollisions(){
        for (int i=0; i< eggs.size(); i++){
            Egg egg = eggs.get(i);
            if (egg.getBounds().intersects(getFarmer().getBounds())){
                eggsColected++;
                SwingUtilities.invokeLater(() -> menuPanel.updateCounterEggs(eggsColected, eggCollectedDeatenation));
                eggs.remove(i);

                int newX = random.nextInt(width - Egg.SIZE + 1);
                int newY = random.nextInt(MARGIN_TOP, height - Egg.SIZE - MARGIN_BOTTOM);

                eggs.add(new Egg(newX, newY, width, height, this.MARGIN_TOP));
                if (!eggs.isEmpty()) {
                    System.out.println(
                            "Last Egg position: x=" + eggs.get(eggs.size() - 1).getX() +
                                    ", y=" + eggs.get(eggs.size() - 1).getY()
                    );
                }

                i=0;

            }
        }

        for (int i=0; i< blackChickens.size(); i++){
            BlackChicken blackChicken = blackChickens.get(i);
            if (blackChicken.getBounds().intersects(getFarmer().getBounds())){
                this.farmer.die();

            }
        }

    }

    /**
     * Renders all game elements:
     * - Chickens, black chickens, eggs, farmer
     * - Countdown timers above eggs
     * - Messages for pause, game over, win
     */
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
                int textX = egg.getX();
                int textY = egg.getY() - 5;

                FontMetrics fm = graphics.getFontMetrics();
                int textWidth = fm.stringWidth(secondsLeft + "s");
                int textHeight = fm.getHeight();

                if (textY < textHeight) {
                    textY = egg.getY() + Egg.SIZE + textHeight / 2;
                }

                if (textX + textWidth > this.width) {
                    textX = this.width - textWidth - 2;
                }

                graphics.drawString(secondsLeft + "s", textX, textY);


            }

            this.farmer.paint(graphics);

            for (BlackChicken blackChicken : this.blackChickens){
                blackChicken.paint(graphics);
            }


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

    /**
     * Returns the Farmer object.
     */
    public Farmer getFarmer () {
        return this.farmer;
    }

    /**
     * Returns number of collected eggs.
     */
    public int getEggsColected (){
        return this.eggsColected;
    }

    public int getHeight(){
        return height;
    }

    /**
     * Assigns movement listener to farmer.
     */
    public void setMovementListener(MovementListener listener) {
        this.movementListener = listener;
    }

    /**
     * Returns target number of eggs to collect.
     */
    public int getEggCollectedDeatenation(){
        return eggCollectedDeatenation;
    }


    /**
     * Returns current number of black chickens.
     */
    public int getNumberOfBlackChickens() {
        return numberOfBlackChickens;
    }

    /**
     * Increases number of black chickens and updates menu display.
     */
    public void increaseNumberOfBlackChickens() {
        this.numberOfBlackChickens++;
        SwingUtilities.invokeLater(() -> menuPanel.updateNumberOfBlackChickens());
    }


    /**
     * Decreases number of black chickens and updates menu display.
     */
    public void decreaseNumberOfBlackChickens() {
        this.numberOfBlackChickens--;
        SwingUtilities.invokeLater(() -> menuPanel.updateNumberOfBlackChickens());
    }

    /**
     * Removes expired eggs, increases broken egg counter,
     * and spawns new eggs.
     */
    public void checkEggsExpiration() {
        for (int i = 0; i < eggs.size(); i++) {
            Egg egg = eggs.get(i);
            if (egg.isExpired()) {
                brokenEggs++;
                SwingUtilities.invokeLater(() -> menuPanel.updateCounterBrokenEggs(this.brokenEggs, this.brokenEggsThreshold));
                eggs.remove(i);


                int newX = random.nextInt(width - Egg.SIZE + 1);
                int newY = random.nextInt(MARGIN_TOP, height - Egg.SIZE - MARGIN_BOTTOM);

                eggs.add(new Egg(newX, newY, width, height, this.MARGIN_TOP));
                if (!eggs.isEmpty()) {
                    System.out.println(
                            "Last Egg position: x=" + eggs.get(eggs.size() - 1).getX() +
                                    ", y=" + eggs.get(eggs.size() - 1).getY()
                    );
                }


                i--;
            }
        }
    }

}

