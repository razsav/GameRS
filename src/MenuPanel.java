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

    public MenuPanel (int x, int y, int width, int height, int windowWidth, int windowHeight, ActionListener instructionsListener) {
        this.setBounds(x, y, width, height);
        this.setLayout(null);
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.windowWidth= windowWidth;
        this.windowHeight= windowHeight;



        JPanel internalMainMenuPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(9, 1); //מסדר את החלקוה לכפתורים ואיך יהיו מסודרים
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

        instructionButton.addActionListener(instructionsListener);
        playButton.addActionListener(e -> scenePanel.setPlay());
        restartButton.addActionListener(e -> scenePanel.startNewGame());
        stopButton.addActionListener(e -> scenePanel.stopGame());
        pauseButton.addActionListener(e-> scenePanel.puseGame());
        continueButton.addActionListener(e-> scenePanel.continueGame());


        internalMainMenuPanel.add(playButton);
        internalMainMenuPanel.add(stopButton);
        internalMainMenuPanel.add(pauseButton);
        internalMainMenuPanel.add(continueButton);
        internalMainMenuPanel.add(restartButton);
        internalMainMenuPanel.add(instructionButton);

        counterEggs = new JLabel("Eggs: 0");
        counterEggs.setFont(new Font("Arial", Font.BOLD, 10));
        counterEggs.setForeground(Color.BLACK);
        internalMainMenuPanel.add(counterEggs);

        counterBrokenEggs = new JLabel("Broken Eggs: 0/5");
        counterBrokenEggs.setFont(new Font("Arial", Font.BOLD, 10));
        counterBrokenEggs.setForeground(Color.BLACK);
        internalMainMenuPanel.add(counterBrokenEggs);


        JPanel movementPanel = new JPanel();
        movementPanel.setBounds(0,height/4, width, height/4);
        movementPanel.setLayout(gridLayout);


        this.add(internalMainMenuPanel, BorderLayout.NORTH);

        this.add(movementPanel, BorderLayout.SOUTH);
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
    public void updateCounterEggs(int eggsCollected) {
        this.counterEggs.setText("Eggs: " + eggsCollected);
    }

    public void updateCounterBrokenEggs(int BrokenEggs, int brokenEggsThreshold) {
        this.counterBrokenEggs.setText("Broken Eggs: " + BrokenEggs + "/" + brokenEggsThreshold);
    }


}



