import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private ScenePanel scenePanel;
    int x, y, width, height, windowWidth, windowHeight;
    MovementListener movementListener;
    private JLabel counterEggs;
    private JLabel counterBrokenEggs;
    private JLabel numberOfBlackChickens;

    public MenuPanel (int x, int y, int width, int height, int windowWidth, int windowHeight) {
        this.setBounds(x, y, width, height);
        this.setLayout(null);
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.windowWidth= windowWidth;
        this.windowHeight= windowHeight;



        JPanel internalMainMenuPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(12, 1); //מסדר את החלקוה לכפתורים ואיך יהיו מסודרים
        internalMainMenuPanel.setLayout(gridLayout);
        internalMainMenuPanel.setBounds(0,0, width, height/4);

        this.scenePanel= new ScenePanel(
                        this.width,
                        0,
                        this.windowWidth-this.width,
                        this.height,
                        null,
                        false,this
        );

        // עכשיו ליצור MovementListener *עם* ה־ScenePanel
        this.movementListener = new MovementListener(this.scenePanel);

// להוסיף את ה־KeyListener לחלון
        this.scenePanel.addKeyListener(this.movementListener);
        this.scenePanel.setFocusable(true);
        this.scenePanel.requestFocusInWindow(); // 👈 מכריח את ScenePanel לקבל פוקוס

// לשמור את ה־movementListener בתוך ה־ScenePanel
        this.scenePanel.setMovementListener(this.movementListener);


        JButton playButton = new JButton("play");
        JButton stopButton = new JButton("stop");
        JButton instructionButton = new JButton("Instruction");
        JButton restartButton = new JButton("Restart");
        JButton pauseButton = new JButton("Pause");
        JButton continueButton = new JButton("Continue");
        JButton moreBlackChickenButton = new JButton("^");
        JButton lessBlackChickenButton = new JButton("v");

        instructionButton.addActionListener(e -> {
            // לעצור את המשחק
            scenePanel.puseGame();

            // יצירת חלון הוראות חדש
            JDialog instructionsDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Instructions", true);
            instructionsDialog.setSize(400, 300);
            instructionsDialog.setLocationRelativeTo(this);

            JTextArea instructionsText = new JTextArea(
                    "Farmer vs Chickens – Game Instructions\n\n" +
                            "Objective:\n" +
                            "Control the farmer, collect eggs, and avoid black chickens.\n" +
                            "The game ends if you collide with a black chicken, break too many eggs, or achieve the win condition.\n\n" +

                            "Controls:\n" +
                            "- Arrow Keys: Move the farmer (Up, Down, Left, Right).\n" +
                            "- Avoid black chickens – colliding with them will end the game.\n" +
                            "- Collect eggs carefully – breaking eggs will count against you.\n\n" +

                            "Game Rules:\n" +
                            "- If you win, collide with a black chicken, or break too many eggs, the game stops.\n" +
                            "- To start a new game, press the 'Restart' button.\n" +
                            "- To adjust the number of black chickens:\n" +
                            "    1. Use the arrow buttons next to the black chicken counter.\n" +
                            "    2. Press 'Restart' to apply the new number.\n\n" +

                            "Tips:\n" +
                            "- Stay away from black chickens.\n" +
                            "- Avoid breaking eggs — too many broken eggs will cause you to lose.\n" +
                            "- Use your speed wisely to collect as many eggs as possible."
            );
            instructionsText.setEditable(false);
            instructionsText.setWrapStyleWord(true);
            instructionsText.setLineWrap(true);
            instructionsDialog.add(new JScrollPane(instructionsText));

            // כשנסגר החלון → להמשיך את המשחק ולהחזיר פוקוס
            instructionsDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    scenePanel.continueGame();
                    scenePanel.requestFocusInWindow();
                }

                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    scenePanel.continueGame();
                    scenePanel.requestFocusInWindow();
                }
            });

            instructionsDialog.setVisible(true);
        });

        playButton.addActionListener(e -> {
            scenePanel.setPlay();
            this.scenePanel.requestFocusInWindow();
        });
        restartButton.addActionListener(e -> {
            scenePanel.startNewGame();
            this.scenePanel.requestFocusInWindow();
        });
        stopButton.addActionListener(e -> {
            scenePanel.stopGame();
            this.scenePanel.requestFocusInWindow();
        });
        pauseButton.addActionListener(e-> {
            scenePanel.puseGame();
            this.scenePanel.requestFocusInWindow();
        });
        continueButton.addActionListener(e-> {
            scenePanel.continueGame();
            this.scenePanel.requestFocusInWindow();
        });

        moreBlackChickenButton.addActionListener(e-> {
            scenePanel.increaseNumberOfBlackChickens();
            scenePanel.requestFocusInWindow();

        });

        lessBlackChickenButton.addActionListener(e-> {
            scenePanel.decreaseNumberOfBlackChickens();
            scenePanel.requestFocusInWindow();
        });


        internalMainMenuPanel.add(playButton);
        internalMainMenuPanel.add(stopButton);
        internalMainMenuPanel.add(pauseButton);
        internalMainMenuPanel.add(continueButton);
        internalMainMenuPanel.add(restartButton);
        internalMainMenuPanel.add(instructionButton);

        counterEggs = new JLabel("Eggs: 0/" + scenePanel.getEggCollectedDeatenation());
        counterEggs.setFont(new Font("Arial", Font.BOLD, 10));
        counterEggs.setForeground(Color.BLACK);
        internalMainMenuPanel.add(counterEggs);

        counterBrokenEggs = new JLabel("Broken Eggs: 0/5");
        counterBrokenEggs.setFont(new Font("Arial", Font.BOLD, 10));
        counterBrokenEggs.setForeground(Color.BLACK);
        internalMainMenuPanel.add(counterBrokenEggs);

        numberOfBlackChickens = new JLabel("Black Chickens: " + scenePanel.getNumberOfBlackChickens());
        numberOfBlackChickens.setFont(new Font("Arial", Font.BOLD, 10));
        numberOfBlackChickens.setForeground(Color.BLACK);


        internalMainMenuPanel.add(moreBlackChickenButton);
        internalMainMenuPanel.add(numberOfBlackChickens);
        internalMainMenuPanel.add(lessBlackChickenButton);


//        JPanel movementPanel = new JPanel();
//        movementPanel.setBounds(0,height/4, width, height/4);
//        movementPanel.setLayout(gridLayout);


        this.add(internalMainMenuPanel, BorderLayout.NORTH);

//        this.add(movementPanel, BorderLayout.SOUTH);
    }

    public static void setPlayTrue(ScenePanel scenePanel){
        scenePanel.setPlay();
    }

    ActionListener playAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setPlayTrue(scenePanel);
        }
    };

    public ScenePanel getScenePanel(){
        return this.scenePanel;
    }

    // שיטה לעדכון הטקסט
    public void updateCounterEggs(int eggsCollected, int eggsCollectedDestenation) {
        this.counterEggs.setText("Eggs: " + eggsCollected + "/" + eggsCollectedDestenation);
    }

    public void updateCounterBrokenEggs(int BrokenEggs, int brokenEggsThreshold) {
        this.counterBrokenEggs.setText("Broken Eggs: " + BrokenEggs + "/" + brokenEggsThreshold);
    }

    // שיטה לעדכון הטקסט
    public void updateNumberOfBlackChickens() {
        this.numberOfBlackChickens.setText("Black Chickens: " + scenePanel.getNumberOfBlackChickens());
    }


}



