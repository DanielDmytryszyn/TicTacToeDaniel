import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The DatabaseConnector class handles the connection and interaction with a MySQL database.
 * It provides methods to check for updates in the database, reset moves, and add moves to the database.
 * It uses JDBC to establish a connection with the database.
 * Author: Daniel Dmytryszyn
 */
public class DatabaseConnector {

    private Connection connection;

    // Connection parameters
    String url = "jdbc:mysql://195.179.236.154:3306/u457888522_firstDB";
    String username = "u457888522_daniel";
    String password = "Password123";

    Board board;

    TimerTask task;

    /**
     * Constructs a DatabaseConnector object with the specified Board instance.
     * Initializes the database connection using JDBC.
     * Resets the moves in the database.
     *
     * @param board the Board instance associated with the database connector
     */
    public DatabaseConnector(Board board) {
        this.board = board;
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        resetMoves();
    }

    /**
     * Checks for updates in the moves table of the database for the specified sign.
     * Runs as a TimerTask at regular intervals.
     * Updates the board if there are new moves in the database.
     *
     * @param sign the sign (X or O) to check for updates
     */
    public void checkForUpdates(String sign) {
        task = new TimerTask() {
            public void run() {
                try {
                    // Execute a query to check if there are any records in the moves table
                    String queryCheck = "SELECT COUNT(*) FROM moves";
                    Statement statementCheck = connection.createStatement();
                    ResultSet resultSetCheck = statementCheck.executeQuery(queryCheck);

                    resultSetCheck.next();
                    int count = resultSetCheck.getInt(1);

                    if (count == 0) {
                        // No moves in the moves table, handle the case accordingly
                        resultSetCheck.close();
                        statementCheck.close();
                        return;
                    }

                    // Execute a query to check for updates
                    String query = "SELECT * FROM moves";
                    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet resultSet = statement.executeQuery(query);

                    System.out.println("Runnable is running");

                    while (resultSet.next()) {
                        int id = resultSet.getInt("id") + 1;

                        String symbol = resultSet.getString("sign");
                        if (symbol.equalsIgnoreCase(sign)) {
                            return;
                        }
                        String opponent = sign.equalsIgnoreCase("X") ? "O" : "X";
                        if (id != 0) {
                            System.out.println(id + 1);
                            System.out.println("There was an update in the database");
                            board.getButtons().get(id - 1).setText(opponent);
                            resetMoves();

                            board.checkBoardState();

                            board.setPlayer(true);

                            if (board.isFull()) {
                                resetBoard();
                            }
                        }
                    }

                    resultSet.close();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };

        Timer timer = new Timer();
        long delay = 0;
        long period = 1000; // Check for updates every 1 second
        timer.schedule(task, delay, period);
    }

    public void resetBoard() {

        board.changeFontSizes(Board.SYMBOL_FONT_SIZE);

        this.resetMoves();
        board.clearButtons();
    }

    /**
     * Resets the moves in the moves table of the database.
     */
    public void resetMoves() {
        String query = "DELETE FROM moves";

        try {
            if (!connection.isValid(2)) {
                connection = DriverManager.getConnection(url, username, password);
            }

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a move to the moves table of the database with the specified index and sign.
     *
     * @param i    the index of the move
     * @param sign the sign (X or O) of the move
     */
    public void addMoveToDatabase(int i, String sign) {
        String query = "INSERT INTO moves (id, sign) VALUES (" + i + ", '" + sign + "')";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("There was a problem when adding the move to the database");
        }
    }
}
