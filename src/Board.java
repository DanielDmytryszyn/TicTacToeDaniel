import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The Board class represents a simple Tic-Tac-Toe game implemented as a JFrame.
 * It allows two players to play the game by clicking on the buttons representing the game board.
 * The game keeps track of the number of wins for each player and the number of draws.
 * The class uses Java Swing for the graphical user interface.
 * Author: Daniel Dmytryszyn
 */
public class Board extends JFrame {

    private final ArrayList<JButton> buttons = new ArrayList<>();
    private boolean player = true;

    private int winCountX = 0;
    private int winCountO = 0;

    public static final int SYMBOL_FONT_SIZE = 20;
    public static final int NORMAL_FONT_SIZE = 5;

    /**
     * Constructs a new Board instance.
     * Initializes the JFrame with the game board and sets up the necessary components.
     */
    public Board() {
        super("The active player is player: X");
        createUI();
        createButtons();
        addButtons();
    }

    /**
     * Creates the user interface for the game board.
     * Sets the layout, size, and visibility of the JFrame.
     */
    private void createUI() {
        setLayout(new GridLayout(3, 3));
        setBounds(0, 0, 600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(getBounds().width, getBounds().height));
        setVisible(true);
    }

    /**
     * The main method that starts the Tic-Tac-Toe game.
     * Creates a new instance of the Board class.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Board();
    }

    /**
     * Adds all the buttons to the JFrame.
     */
    public void addButtons() {
        buttons.forEach(this::add);
    }

    /**
     * Creates the buttons representing the game board.
     */
    public void createButtons() {
        for (int y = 0; y < 9; y++) {
            JButton button = initializeButton();
            buttons.add(button);
        }
    }

    /**
     * Writes the win message and updates the win count based on the winning symbol.
     *
     * @param symbol the symbol (X or O) of the winning player
     */
    public void writeWinMessage(String symbol) {
        changeFontSizes(NORMAL_FONT_SIZE);
        writeToAllButtons(symbol + " has won, click to Reset".toUpperCase());
        setTitle(symbol + " has won".toUpperCase());

        if (Objects.equals(symbol, "X")) winCountX++;
        else winCountO++;
    }

    /**
     * Checks the state of the game board to determine if there is a winner or a draw.
     * If a player has won, it writes the win message.
     * If the game is a draw, it writes the draw message.
     * If neither, it continues the game.
     */
    public void checkBoardState() {
        if (checkWin("X")) {
            writeWinMessage("X");
            return;
        }
        if (checkWin("O")) {
            writeWinMessage("0");
            return;
        }

        if (isFull()) {
            changeFontSizes(NORMAL_FONT_SIZE);
            writeToAllButtons("Draw, click to reset".toUpperCase());
            setTitle("Draw".toUpperCase());
        }
    }

    /**
     * Initializes a button with its properties and adds an action listener to handle button clicks.
     *
     * @return the initialized button
     */
    private JButton initializeButton() {
        JButton button = new JButton();
        button.setBounds(1, 1, 100, 75);
        button.setFont(new Font("Arial", Font.BOLD, SYMBOL_FONT_SIZE));

        return button;
    }


    /**
     * Writes the given text to all the buttons.
     *
     * @param text the text to write to the buttons
     */
    public void writeToAllButtons(String text) {
        buttons.forEach(jButton -> jButton.setText(text));
    }

    /**
     * Changes the font size of all the buttons.
     *
     * @param fontSize the new font size to set
     */
    public void changeFontSizes(int fontSize) {
        buttons.forEach(jButton -> jButton.setFont(new Font("Arial", Font.BOLD, fontSize)));
    }

    /**
     * Clears the text of all the buttons.
     */
    public void clearButtons() {
        buttons.forEach(jButton -> jButton.setText(""));
    }

    /**
     * Checks if the game board is full (all buttons are filled).
     *
     * @return true if the game board is full, false otherwise
     */
    public boolean isFull() {
        long emptyButtonCount = buttons.stream().filter(jButton -> jButton.getText().equals("")).count();
        return emptyButtonCount == 0;
    }

    /**
     * Checks if the given symbol has won the game by checking all possible winning combinations.
     *
     * @param symbol the symbol (X or O) to check
     * @return true if the symbol has won, false otherwise
     */
    private boolean checkWin(String symbol) {
        return win(symbol, buttons);
    }

    /**
     * Checks if the given symbol has won the game by checking all possible winning combinations.
     *
     * @param symbol  the symbol (X or O) to check
     * @param buttons the list of buttons representing the game board
     * @return true if the symbol has won, false otherwise
     */
    static boolean win(String symbol, ArrayList<JButton> buttons) {
        for (int i = 0; i < 9; i += 3) {
            if (buttons.get(i).getText().equals(symbol) && buttons.get(i + 1).getText().equals(symbol) && buttons.get(i + 2).getText().equals(symbol)) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (buttons.get(i).getText().equals(symbol) && buttons.get(i + 3).getText().equals(symbol) && buttons.get(i + 6).getText().equals(symbol)) {
                return true;
            }
        }

        if (buttons.get(0).getText().equals(symbol) && buttons.get(4).getText().equals(symbol) && buttons.get(8).getText().equals(symbol)) {
            return true;
        }
        return buttons.get(2).getText().equals(symbol) && buttons.get(4).getText().equals(symbol) && buttons.get(6).getText().equals(symbol);
    }

    /**
     * returns the list of buttons representing the game board.
     */
    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    /**
     * returns the boolean value of player.
     */
    public boolean isPlayer() {
        return player;
    }

    /**
     * returns the integer value of winCountX.
     */
    public int getWinCountX() {
        return winCountX;
    }

    /**
     * returns the integer value of winCountO.
     */
    public int getWinCountO() {
        return winCountO;
    }

    /**
     * sets the boolean value of player.
     */
    public void setPlayer(boolean player) {
        this.player = player;
    }
}
