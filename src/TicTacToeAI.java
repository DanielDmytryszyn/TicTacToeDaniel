import javax.swing.*;

/**
 * The TicTacToeAI class represents a Tic-Tac-Toe game with an AI opponent, implemented as a JFrame.
 * It allows the player to play against the AI by clicking on the buttons representing the game board.
 * The game keeps track of the moves made by the player and the AI, and determines the winner or a draw.
 * The class uses Java Swing for the graphical user interface.
 * Author: Daniel Dmytryszyn
 */
public class TicTacToeAI extends JFrame {

    private final TicTacToeEnemy enemy; // AI opponent for the game
    private final Board board; // The game board

    public static final String humanChar = "X";
    public static final String aiChar = "O";

    /**
     * Constructs a new TicTacToeAI object.
     * Initializes the JFrame with the title and layout.
     * Sets the size and position of the JFrame.
     * Creates the buttons for the game board.
     * Initializes the AI opponent.
     */
    public TicTacToeAI() {
        super("Your Symbol is " + humanChar);
        board = new Board();
        enemy = new TicTacToeEnemy(board.getButtons(), aiChar.charAt(0));
        initializeButtons();
    }

    /**
     * Initializes the buttons on the game board with appropriate properties and event listeners.
     */
    private void initializeButtons() {
        board.getButtons().forEach(button -> button.addActionListener(e -> {
            if (board.isFull()) {
                board.clearButtons();
                board.changeFontSizes(Board.SYMBOL_FONT_SIZE);
            } else {
                makeMove(button);
                board.checkBoardState();
            }
        }));
    }

    /**
     * Makes a move on the game board by the human player.
     * Checks if the button is empty, updates the board, and calls the AI opponent to make a move.
     * Updates the title of the JFrame with the game statistics.
     *
     * @param button The button representing the move made by the human player.
     */
    private void makeMove(JButton button) {
        if (button.getText().equals("")) {
            button.setText(humanChar);
            board.checkBoardState();
            enemy.makeMove();
        }
    }
}
