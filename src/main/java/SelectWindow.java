import com.formdev.flatlaf.FlatDarculaLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

public class SelectWindow implements ActionListener { //Must implement ActionListener so that we can use "this" in dynamicButtons for addActionListener

    ArrayList<Character> characters;
    JFrame frame;
    JPanel panel;
    JPanel panel2;
    JButton[] buttons;
    JButton btn;
    JButton rndBTN;
    JLabel currentSelected;
    Character current;
    Character player1;
    Character player2;
    boolean again;

    public SelectWindow(ArrayList<Character> characters, Character player1, Character player2, String title, boolean again) {
        this.characters = characters;
        this.player1 = player1;
        this.player2 = player2;
        this.again = again;

        File style = new File("src/main/java/style/");
        FlatDarculaLaf.registerCustomDefaultsSource(style);
        FlatDarculaLaf.setup();

        frame = new JFrame("Select the character for " + title);
        frame.setVisible(true);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null); //centers the window
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Ask for confirmation before terminating the program.
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to close the application?", "Close Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        panel = new JPanel();
        panel2 = new JPanel();

        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.SOUTH);

        dynamicButtons();
        submitSection();
    }

    public void dynamicButtons() {
        buttons = new JButton[characters.size()];

        for (int i = 0; i < characters.size(); i++) {
            JButton button = new JButton(characters.get(i).getName());
            button.addActionListener(this);
            panel.add(button);
            buttons[i] = button;
        }

        panel.revalidate();
        panel.repaint();
    }

    public void submitSection() {
        panel2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); //So that it isn't directly at the border of the window

        currentSelected = new JLabel("Current selected character: ");
        rndBTN = new JButton("Random");
        btn = new JButton("Submit");

        btn.addActionListener(this);
        rndBTN.addActionListener(this);

        currentSelected.setAlignmentX(Component.CENTER_ALIGNMENT);
        rndBTN.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel2.add(currentSelected);
        panel2.add(rndBTN);
        panel2.add(btn);

        panel2.revalidate();
        panel2.repaint();
    }

    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == btn) {
            if(current == null) {
                currentSelected.setText("No character selected!");
            }
            else {
                if(again) {
                    try {
                        player1 = (Character) current.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    SelectWindow sWP2 = new SelectWindow(characters, player1, player2, "player 2", false); //Has to be run, as frame.dispose() does NOT trigger the windowsClosing listener.
                }
                else {
                    try {
                        player2 = (Character) current.clone(); //We MUST clone, if we would just do player2 = current, then p1 and p2 could use the same character
                        player2.generateStats(); //Regenerate stats so that if they pick the same char, they won't share stats
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    GameWindow gW = new GameWindow(player1, player2);
                }
                frame.dispose();
            }
        }

        else if(event.getSource() == rndBTN) {
            int rnd = (int)(Math.random() * characters.size());
            currentSelected.setText("Current selected character: Random");
            current = characters.get(rnd);
        }

        else {
            for (int i = 0; i < buttons.length; i++) {
                if (event.getSource() == buttons[i]) {
                    currentSelected.setText("Current selected character: " + characters.get(i).getName());
                    current = characters.get(i);
                }
            }
        }
    }

}