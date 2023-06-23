import javax.swing.*;
import java.util.List;

/**
 * The TicTacToeEnemy class represents an AI opponent for the Tic-Tac-Toe game.
 * It determines the best moves for the AI player based on the current game state.
 * Author: [Author's Name]
 */
public class TicTacToeEnemy {

    private final List<JButton> buttons; // List of buttons representing the game board
    private final char enemySymbol; // Symbol representing the AI player

    /**
     * Constructs a new TicTacToeEnemy object with the given list of buttons and enemy symbol.
     *
     * @param buttons     The list of buttons representing the game board.
     * @param enemySymbol The symbol representing the AI player.
     */
    public TicTacToeEnemy(List<JButton> buttons, char enemySymbol) {
        this.buttons = buttons;
        this.enemySymbol = enemySymbol;
    }

    /**
     * Makes a move on the game board by the AI player.
     * Determines the best move using the minimax algorithm and updates the corresponding button.
     */
    public void makeMove() {
        int moveIndex = findBestMove();
        buttons.get(moveIndex).setText(Character.toString(enemySymbol));
    }

    /**
     * Finds the best move for the AI player using the minimax algorithm.
     *
     * @return The index of the best move in the buttons list.
     * @throws IllegalStateException if no valid move is found.
     */
    private int findBestMove() {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getText().isEmpty()) {
                buttons.get(i).setText(Character.toString(enemySymbol));
                int score = minimax(false);
                buttons.get(i).setText("");
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }

        if (bestMove == -1) {
            throw new IllegalStateException("No valid move found.");
        }

        return bestMove;
    }

    /**
     * Applies the minimax algorithm to determine the best score for the current game state.
     *
     * @param isMaximizingPlayer true if the AI player is the maximizing player, false otherwise.
     * @return The best score for the current game state.
     */
    private int minimax(boolean isMaximizingPlayer) {
        if (checkWin(enemySymbol)) {
            return 1;
        } else if (checkWin('X')) {
            return -1;
        } else if (isBoardFull()) {
            return 0;
        }

        int bestScore;
        if (isMaximizingPlayer) {
            bestScore = Integer.MIN_VALUE;

            for (JButton button : buttons) {
                if (button.getText().isEmpty()) {
                    button.setText(Character.toString(enemySymbol));
                    int score = minimax(false);
                    button.setText("");
                    bestScore = Math.max(score, bestScore);
                }
            }

        } else {
            bestScore = Integer.MAX_VALUE;

            for (JButton button : buttons) {
                if (button.getText().isEmpty()) {
                    button.setText("X");
                    int score = minimax(true);
                    button.setText("");
                    bestScore = Math.min(score, bestScore);
                }
            }

        }
        return bestScore;
    }

    /**
     * Checks if the given symbol has won the game.
     *
     * @param symbol The symbol to check for a win.
     * @return true if the symbol has won the game, false otherwise.
     */
    private boolean checkWin(char symbol) {
        // Check rows for a win
        if (buttons.get(0).getText().equals(Character.toString(symbol)) && buttons.get(1).getText().equals(Character.toString(symbol)) && buttons.get(2).getText().equals(Character.toString(symbol)))
            return true;
        if (buttons.get(3).getText().equals(Character.toString(symbol)) && buttons.get(4).getText().equals(Character.toString(symbol)) && buttons.get(5).getText().equals(Character.toString(symbol)))
            return true;
        if (buttons.get(6).getText().equals(Character.toString(symbol)) && buttons.get(7).getText().equals(Character.toString(symbol)) && buttons.get(8).getText().equals(Character.toString(symbol)))
            return true;

        // Check columns for a win
        if (buttons.get(0).getText().equals(Character.toString(symbol)) && buttons.get(3).getText().equals(Character.toString(symbol)) && buttons.get(6).getText().equals(Character.toString(symbol)))
            return true;
        if (buttons.get(1).getText().equals(Character.toString(symbol)) && buttons.get(4).getText().equals(Character.toString(symbol)) && buttons.get(7).getText().equals(Character.toString(symbol)))
            return true;
        if (buttons.get(2).getText().equals(Character.toString(symbol)) && buttons.get(5).getText().equals(Character.toString(symbol)) && buttons.get(8).getText().equals(Character.toString(symbol)))
            return true;

        // Check diagonals for a win
        if (buttons.get(0).getText().equals(Character.toString(symbol)) && buttons.get(4).getText().equals(Character.toString(symbol)) && buttons.get(8).getText().equals(Character.toString(symbol)))
            return true;
        return buttons.get(2).getText().equals(Character.toString(symbol)) && buttons.get(4).getText().equals(Character.toString(symbol)) && buttons.get(6).getText().equals(Character.toString(symbol));
    }

    /**
     * Checks if the game board is full.
     *
     * @return true if the game board is full, false otherwise.
     */
    private boolean isBoardFull() {
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
