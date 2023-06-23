import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * The TicTacToeLobby class represents the lobby window for the Tic-Tac-Toe game.
 * It allows the user to choose a game mode: Human vs. Human, Human vs. Computer, or Online mode.
 * Author: Daniel Dmytryszyn
 */
public class TicTacToeLobby extends JFrame {

    private final JButton human = new JButton();
    private final JButton computer = new JButton();
    private final JButton online = new JButton();

    /**
     * Constructs a new TicTacToeLobby object.
     * Initializes the lobby window by setting the layout, bounds, and title.
     * Sets the minimum size, adds the human and computer buttons to the layout, and initializes the button actions.
     */
    public TicTacToeLobby() {
        super("Choose a game mode");
        createUI();
        createPlayers();
    }

    /**
     * Creates the UI for the lobby window.
     * Sets the layout, bounds, and visibility of the frame.
     */
    private void createUI() {
        setLayout(new GridLayout(1, 3));
        setBounds(300, 200, 1500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(getBounds().width, getBounds().height));
        setVisible(true);
    }

    /**
     * Adds the buttons for different game modes to the lobby window.
     * Initializes the button actions for each button.
     */
    private void createPlayers() {
        this.add(human);
        this.add(computer);
        this.add(online);
        initializeHuman();
        initializeComputer();
        initializeOnline();
    }

    /**
     * Initializes the "Human" button.
     * Sets the font, text, and action for the "Human" button.
     * When the button is clicked, it creates a new TicTacToe object.
     */
    private void initializeHuman() {
        human.setFont(new Font("Arial", Font.BOLD, 50));
        human.setText("Human");

        human.addActionListener(actionEvent -> new TicTacToe());
    }

    /**
     * Initializes the "Online" button.
     * Sets the font, text, and action for the "Online" button.
     * When the button is clicked, it creates a new TicTacToeOnline object and closes the lobby window.
     */
    private void initializeOnline() {
        online.setFont(new Font("Arial", Font.BOLD, 50));
        online.setText("Online");

        online.addActionListener(actionEvent -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            new TicTacToeOnline();
        });
    }

    /**
     * Initializes the "Computer" button.
     * Sets the font, text, and action for the "Computer" button.
     * When the button is clicked, it creates a new TicTacToeAI object.
     */
    private void initializeComputer() {
        computer.setFont(new Font("Arial", Font.BOLD, 50));
        computer.setText("Computer");

        computer.addActionListener(actionEvent -> new TicTacToeAI());
    }

    /**
     * The main method of the TicTacToeLobby class.
     * It creates a new TicTacToeLobby object, which displays the lobby window.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        new TicTacToeLobby();
    }
}
