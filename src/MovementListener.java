/**
 * MovementListener - Listens for arrow key presses and releases
 * and keeps track of movement states (up, down, left, right).
 * Used by the ScenePanel to control the Farmer.
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovementListener implements KeyListener {
    private ScenePanel scenePanel;
    private boolean right;
    private boolean left;
    private boolean up;
    private boolean down;

    /**
     * Creates a MovementListener attached to a ScenePanel.
     * @param scenePanel the game panel this listener controls
     */
    public MovementListener(ScenePanel scenePanel) {
        this.scenePanel = scenePanel;
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * Updates movement states when a key is pressed.
     * @param e the KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT -> this.right = true;
            case KeyEvent.VK_LEFT -> this.left = true;
            case KeyEvent.VK_UP -> this.up = true;
            case KeyEvent.VK_DOWN -> this.down = true;
        }

    }

    /** @return true if RIGHT arrow is pressed */
    public boolean isRight() { return right; }

    /** @return true if LEFT arrow is pressed */
    public boolean isLeft() { return left; }

    /** @return true if UP arrow is pressed */
    public boolean isUp() { return up; }

    /** @return true if DOWN arrow is pressed */
    public boolean isDown() { return down; }

    /**
     * Updates movement states when a key is released.
     * @param e the KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT -> this.right = false;
            case KeyEvent.VK_LEFT -> this.left = false;
            case KeyEvent.VK_UP -> this.up = false;
            case KeyEvent.VK_DOWN -> this.down = false;
        }
    }
}
