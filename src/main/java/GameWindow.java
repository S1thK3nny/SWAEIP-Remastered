import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow {

    Character player1;
    Character player2;
    GameEvents gameEvents;
    JButton[] attackButtons;
    JFrame frame;
    JLabel player1Health;
    JLabel player2Health;
    JPanel topPanel;
    JPanel bottomPanel;
    JPanel buttonPanel;
    JPanel advancedButtonPanel;
    JPanel attackButtonPanel;
    JPanel itemButtonPanel;
    JPanel surrenderButtonPanel;
    JScrollPane chatScrollPane;
    JTextArea chatArea;

    public GameWindow(Character player1, Character player2) {
        this.player1 = player1;
        this.player2 = player2;

        FlatDarculaLaf.setup();

        // Create a new frame
        frame = new JFrame("SWAEIP: Dawn of Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null); //centers the window
        frame.setLayout(new BorderLayout());
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

        // Create a new panel for the top half of the window
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // Create a new panel for the bottom half of the window
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        // We must first add the bottom panel, so when the window gets resized, the buttons will be on top!
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(topPanel, BorderLayout.NORTH);

        loggingArea();
        basicButtons();
        advancedButtons();

        // This must be at the bottom...I do not know why
        frame.setVisible(true);

        gameEvents = new GameEvents(chatArea);
        gameEvents.startGameThread();
    }

    public void loggingArea() {
        chatArea = new JTextArea(20, 20);
        chatArea.setEditable(false); // set the chat area to be read-only
        chatArea.setLineWrap(true); // wrap lines when they reach the end of the text area
        chatArea.setWrapStyleWord(true); // wrap lines on whole words
        chatArea.setFont(new Font("Arial", Font.PLAIN, 15));

        chatScrollPane = new JScrollPane(chatArea); // wrap the text area in a scroll pane
        topPanel.add(chatScrollPane, BorderLayout.CENTER);

        // toString() does NOT work here...or at least not well
        chatArea.append("Player 1 (You): " + "\tName: " + player1.getName() + "\n\tTier: " + player1.getTier() + "\n\tHealth: " + player1.getHealth() + "\n\tAttacks: " + player1.getAttacks() + "\n\n");
        chatArea.append("Player 2: " + "\tName: " + player2.getName() + "\n\tTier: " + player2.getTier() + "\n\tHealth: " + player2.getHealth() + "\n\tAttacks: " + player2.getAttacks() + "\n\n");
    }

    public void basicButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton attacksButton = new JButton("Attacks");
        attacksButton.addActionListener(e -> {
            //chatArea.append("Attacks button clicked\n");
            if(attackButtonPanel.isVisible()) {
                attackButtonPanel.setVisible(false);
            }
            else {
                itemButtonPanel.setVisible(false);
                surrenderButtonPanel.setVisible(false);
                attackButtonPanel.setVisible(true);
            }
        });
        attacksButton.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(attacksButton, BorderLayout.SOUTH);

        JButton itemsButton = new JButton("Items");
        itemsButton.addActionListener(e -> {
            //chatArea.append("Items button clicked\n");
            if(itemButtonPanel.isVisible()) {
                itemButtonPanel.setVisible(false);
            }
            else {
                attackButtonPanel.setVisible(false);
                surrenderButtonPanel.setVisible(false);
                itemButtonPanel.setVisible(true);
            }
        });
        itemsButton.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(itemsButton, BorderLayout.SOUTH);

        JButton surrenderButton = new JButton("Surrender");
        surrenderButton.addActionListener(e -> {
            //chatArea.append("Surrender button clicked\n");
            if(surrenderButtonPanel.isVisible()) {
                surrenderButtonPanel.setVisible(false);
            }
            else {
                attackButtonPanel.setVisible(false);
                itemButtonPanel.setVisible(false);
                surrenderButtonPanel.setVisible(true);
            }
        });
        surrenderButton.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(surrenderButton, BorderLayout.SOUTH);

        player1Health = new JLabel("Player 1 Health: " + player1.getHealth());
        player2Health = new JLabel("Player 2 Health: " + player2.getHealth());

        player1.healthChange.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("health")) {
                player1Health.setText("Player 1 Health: " + player1.getHealth());
            }
        });

        player2.healthChange.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("health")) {
                player2Health.setText("Player 2 Health: " + player2.getHealth());
            }
        });



        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(player1Health, BorderLayout.WEST);
        bottomPanel.add(player2Health, BorderLayout.EAST);
    }

    public void advancedButtons() {
        advancedButtonPanel = new JPanel();
        advancedButtonPanel.setLayout(new FlowLayout());
        advancedButtonPanel.setVisible(true);

        attackButtonPanel = new JPanel();
        attackButtonPanel.setLayout(new FlowLayout());
        attackButtonPanel.setVisible(false);

        itemButtonPanel = new JPanel();
        itemButtonPanel.setLayout(new FlowLayout());
        itemButtonPanel.setVisible(false);

        surrenderButtonPanel = new JPanel();
        surrenderButtonPanel.setLayout(new FlowLayout());
        surrenderButtonPanel.setVisible(false);



        attackButtons = new JButton[player1.getAttacks().size()];
        for(int i=0; i<player1.getAttacks().size(); i++) {
            JButton button = new JButton(player1.getAttacks().get(i).getName());
            //button.addActionListener(this);
            attackButtonPanel.add(button);
            attackButtons[i] = button;
        }


        JButton buttonItem1 = new JButton("TempItem1");
        JButton buttonItem2 = new JButton("TempItem2");
        JButton buttonItem3 = new JButton("TempItem3");
        JButton buttonItem4 = new JButton("TempItem4");
        itemButtonPanel.add(buttonItem1, BorderLayout.CENTER);
        itemButtonPanel.add(buttonItem2, BorderLayout.CENTER);
        itemButtonPanel.add(buttonItem3, BorderLayout.CENTER);
        itemButtonPanel.add(buttonItem4, BorderLayout.CENTER);


        JButton surrenderYes = new JButton("Confirm surrender?");
        surrenderButtonPanel.add(surrenderYes, BorderLayout.CENTER);
        surrenderYes.addActionListener(e -> {
            chatArea.append("\nYou lost!");
            gameEvents.stopGameThread();
        });



        advancedButtonPanel.add(attackButtonPanel, BorderLayout.CENTER);
        advancedButtonPanel.add(itemButtonPanel, BorderLayout.CENTER);
        advancedButtonPanel.add(surrenderButtonPanel, BorderLayout.CENTER);
        bottomPanel.add(advancedButtonPanel, BorderLayout.NORTH);
    }
}