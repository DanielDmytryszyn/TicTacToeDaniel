import javax.swing.*;

/**
 * The TicTacToeOnline class represents the online multiplayer version of the Tic-Tac-Toe game.
 * It allows two players to play against each other over a network connection.
 */
public class TicTacToeOnline {

    private final Board board = new Board();
    private final DatabaseConnector db;
    private final String sign;

    /**
     * Constructs a new TicTacToeOnline object.
     * Initializes the game board, database connector, and player's sign.
     * Prompts the user to enter their sign (X or O).
     * Sets the title of the board and the current player based on the sign.
     * Initializes the buttons on the board and starts checking for updates from the database.
     */
    public TicTacToeOnline() {
        db = new DatabaseConnector(board);
        sign = JOptionPane.showInputDialog("Enter your sign").toUpperCase();
        board.setTitle("You are " + sign);

        if (sign.equalsIgnoreCase("X")) {
            board.setPlayer(true);
        }

        initializeButtons();
        db.checkForUpdates(sign);
        db.task.run();
    }

    /**
     * Initializes the buttons on the game board.
     * Adds an action listener to each button to handle the player's moves.
     * If the button is already occupied or it's not the player's turn, the move is ignored.
     * If a valid move is made, it updates the database with the move and updates the button text accordingly.
     * Checks the board state for a win or draw condition.
     * If the board is full, the game is reset after a delay.
     */
    public void initializeButtons() {
        board.getButtons().forEach(jButton -> jButton.addActionListener(e -> {
            if (!jButton.getText().equals("") || !board.isPlayer()) {
                return;
            }

            int index = board.getButtons().indexOf(jButton);
            db.addMoveToDatabase(index, sign);

            jButton.setText(sign);
            board.setPlayer(false);


            board.checkBoardState();

            if (board.isFull()) {
                db.resetBoard();
            }
        }));
    }

    /**
     * The main method of the TicTacToeOnline class.
     * It creates a new TicTacToeOnline object, which starts the online multiplayer game.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        new TicTacToeOnline();
    }
}
