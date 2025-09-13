import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static final int GAME_WINDOW_WIDTH= 800;
    public static final int GAME_WINDOW_HEIGHT= 600;
//    public static final int chickensAmount= 5;


    private static void showInstructions(){
        String instructions = "Instructions for the Game:\n" +
                "\n" +
                "## The Goal\n" +
                "In this game, you are **Huey the Chicken**, and your goal is to collect eggs laid by other chickens in the barnyard. Collect a specific number of eggs to win the game.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Gameplay\n" +
                "Chickens will lay eggs that appear on the ground. You must move to the eggs and collect them before they disappear.\n" +
                "\n" +
                "Each egg is only allowed to stay on the ground for a **limited time** once it is laid. An egg that isn't collected in time will count as a miss.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Winning & Losing\n" +
                "* **You win** when you successfully collect the target number of eggs.\n" +
                "* **You lose** if you accumulate a specific number of misses.";

        JOptionPane.showMessageDialog(null, instructions, "Game Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void startGame (){

    }

    public static void main(String[]args){

        ActionListener instructionsAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInstructions();
            }
        };


        JFrame gameWindow = new JFrame();
        gameWindow.setFocusable(true);
        gameWindow.setSize(GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT);
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setLayout(null);

        gameWindow.setTitle("gameName");

        MenuPanel menuPanel = new MenuPanel(0, 0, GAME_WINDOW_WIDTH / 8, GAME_WINDOW_HEIGHT,GAME_WINDOW_WIDTH,GAME_WINDOW_HEIGHT, instructionsAction);
        gameWindow.add(menuPanel);

        MovementListener movementListener = new MovementListener(null);

//        ScenePanel scenePanel = new ScenePanel
//                (
//                menuPanel.getWidth(),
//                        0,
//                        gameWindow.getWidth()-menuPanel.getWidth(),
//                        GAME_WINDOW_HEIGHT,
//                        chickensAmount,
//                        movementListener,
//                        false
//                        );

        gameWindow.addKeyListener(movementListener);


        gameWindow.add(menuPanel.getScenePanel());
        gameWindow.setVisible(true);
        gameWindow.requestFocusInWindow();

    }

}
