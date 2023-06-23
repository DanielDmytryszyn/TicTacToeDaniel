import javax.swing.*;

/**
 * The TicTacToe class represents a simple Tic-Tac-Toe game implemented as a JFrame.
 * It allows two players to play the game by clicking on the buttons representing the game board.
 * The game keeps track of the number of wins for each player and the number of draws.
 * The class uses Java Swing for the graphical user interface.
 * Author: Daniel Dmytryszyn
 */
public class TicTacToe extends JFrame {

    private final Board board;
    private boolean player = true;

    /**
     * Constructs a new TicTacToe instance.
     * Initializes the JFrame with the game board and sets up the necessary components.
     */
    public TicTacToe() {
        super("The active player is player: X");
        board = new Board();
        initializeButtons();
    }

    /**
     * Initializes the buttons on the game board and adds an action listener to handle button clicks.
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
     * Makes a move by changing the text of the clicked button and updating the active player.
     *
     * @param button the clicked button
     */
    private void makeMove(JButton button) {
        if (button.getText().equals("")) {
            String activePlayer;
            if (player) {
                button.setText("O");
                activePlayer = "X";
            } else {
                button.setText("X");
                activePlayer = "O";
            }

            player = !player;

            setTitle("The active player is player: " + activePlayer + "     " +
                    board.getWinCountX() + " times X has won     " +
                    board.getWinCountO() + " times O has won     " +
                    "     "  + " times the game concluded in a draw");
        }
    }
}
