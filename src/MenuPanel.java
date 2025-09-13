import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private ScenePanel scenePanel;
    int x, y, width, height, windowWidth, windowHeight;
    MovementListener movementListener;

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
        GridLayout gridLayout = new GridLayout(6, 1); //מסדר את החלקוה לכפתורים ואיך יהיו מסודרים
        internalMainMenuPanel.setLayout(gridLayout);
        internalMainMenuPanel.setBounds(0,0, width, height/4);

        this.scenePanel= new ScenePanel(
                        this.width,
                        0,
                        this.windowWidth-this.width,
                        this.height,
                        null,
                        false);

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

        internalMainMenuPanel.add(playButton);
        internalMainMenuPanel.add(stopButton);
        internalMainMenuPanel.add(pauseButton);
        internalMainMenuPanel.add(continueButton);
        internalMainMenuPanel.add(restartButton);
        internalMainMenuPanel.add(instructionButton);


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

//    ActionListener startGame = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            ;
//        }
//    };
    public ScenePanel getScenePanel(){
        return this.scenePanel;
    }


}



