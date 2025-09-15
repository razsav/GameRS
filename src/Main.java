/**
 * Main - Entry point of the Farmer vs Chickens game.
 * Sets up the main game window, creates MenuPanel and ScenePanel,
 * and attaches the movement listener.
 */


import javax.swing.*;


public class Main {
    public static final int GAME_WINDOW_WIDTH= 800;
    public static final int GAME_WINDOW_HEIGHT= 600;

    public static void main(String[]args){

        JFrame gameWindow = new JFrame();
        gameWindow.setFocusable(true);
        gameWindow.setSize(GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT);
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setLayout(null);

        gameWindow.setTitle("gameName");

        MenuPanel menuPanel = new MenuPanel(0, 0, GAME_WINDOW_WIDTH / 8, GAME_WINDOW_HEIGHT,GAME_WINDOW_WIDTH,GAME_WINDOW_HEIGHT);
        gameWindow.add(menuPanel);

        MovementListener movementListener = new MovementListener(null);

        gameWindow.addKeyListener(movementListener);

        gameWindow.add(menuPanel.getScenePanel());
        gameWindow.setVisible(true);
        gameWindow.requestFocusInWindow();

    }

}
