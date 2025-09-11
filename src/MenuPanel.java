import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    public MenuPanel (int x, int y, int width, int height, ActionListener instructionsListener) {
        this.setBounds(x, y, width, height);
        this.setLayout(null);


        JPanel internalMainMenuPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(6, 1); //מסדר את החלקוה לכפתורים ואיך יהיו מסודרים
        internalMainMenuPanel.setLayout(gridLayout);
        internalMainMenuPanel.setBounds(0,0, width, height/4);

        JButton playButton = new JButton("play");
        JButton stopButton = new JButton("stop");
        JButton instructionButton = new JButton("Instruction");
        JButton restartButton = new JButton("Restart");
        JButton pauseButton = new JButton("Pause");
        JButton continueButton = new JButton("Continue");

        instructionButton.addActionListener(instructionsListener);

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



}



