package com.example.sudokugame;

/*

 * Main User Interface for Sudoku Game using JavaFX

 * FEATURES:
 *  9x9 grid with TextFields
 *  Real-time validation with color feedback
 *  New Game, Reset, Solve buttons
 *  Puzzle completion detection
*/

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SudokuUI {

    // UI Components
    private Stage stage;
    private TextField[][] cells;
    private int[][] board;
    private int[][] initialBoard;
    private Label solvedLabel;
    private VBox solvedOverlay;


    private SudokuSolver solver;
    private SudokuGenerator generator;

    /**
     * Constructor - Initialize UI

     */
    public SudokuUI(Stage stage) {
        this.stage = stage;
        this.cells = new TextField[9][9];
        this.board = new int[9][9];
        this.initialBoard = new int[9][9];

        this.solver = new SudokuSolver();
        this.generator = new SudokuGenerator();

        setupUI();
    }

    /**
     * complete UI layout
     */
    private void setupUI() {

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));


        VBox topSection = new VBox(15);
        topSection.setAlignment(Pos.CENTER);
        topSection.setPadding(new Insets(10));


        Label title = new Label("SUDOKU GAME");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));


        HBox buttonBox = createButtonPanel();

        topSection.getChildren().addAll(title, buttonBox);
        root.setTop(topSection);


        StackPane centerStack = new StackPane();


        GridPane grid = createSudokuGrid();


        VBox solvedOverlay = new VBox();
        solvedOverlay.setAlignment(Pos.CENTER);
        solvedOverlay.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); -fx-background-radius: 10;");
        solvedOverlay.setPadding(new Insets(30));
        solvedOverlay.setMaxWidth(300);
        solvedOverlay.setMaxHeight(100);

        solvedLabel = new Label(" Solved");
        solvedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        solvedLabel.setTextFill(Color.web("#28a745")); //  green color

        solvedOverlay.getChildren().add(solvedLabel);
        solvedOverlay.setVisible(false); // Hidden initially


        centerStack.getChildren().addAll(grid, solvedOverlay);
        root.setCenter(centerStack);


        this.solvedLabel = solvedLabel;
        this.solvedOverlay = solvedOverlay;


        Scene scene = new Scene(root, 550, 650);
        stage.setTitle("Sudoku Game");
        stage.setScene(scene);
        stage.setResizable(false);


        generateNewPuzzle();
    }

    /*
     * Create the 9x9 Sudoku grid using GridPane

     */
    private GridPane createSudokuGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(0);
        grid.setVgap(0);

        // Create 9x9 TextFields
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                TextField cell = new TextField();
                cell.setPrefSize(50, 50);
                cell.setAlignment(Pos.CENTER);
                cell.setFont(Font.font("Arial", FontWeight.BOLD, 18));


                String borderStyle = "-fx-border-color: black; -fx-border-width: ";


                String topBorder = (row % 3 == 0) ? "2 " : "1 ";


                String rightBorder = (col % 3 == 2) ? "2 " : "1 ";


                String bottomBorder = (row == 8) ? "2 " : "1 ";


                String leftBorder = (col % 3 == 0) ? "2" : "1";

                cell.setStyle(borderStyle + topBorder + rightBorder + bottomBorder + leftBorder + ";");

                // Store reference in 2D array
                cells[row][col] = cell;


                grid.add(cell, col, row);

                // Add input validation listener
                final int r = row;
                final int c = col;
                cell.textProperty().addListener((obs, oldVal, newVal) -> {
                    handleCellInput(r, c, newVal);
                });
            }
        }

        return grid;
    }


    private HBox createButtonPanel() {
        HBox buttonBox = new HBox(15);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);


        Button newGameBtn = new Button("New Game");
        newGameBtn.setPrefWidth(120);
        newGameBtn.setOnAction(e -> generateNewPuzzle());




        Button resetBtn = new Button("Reset");
        resetBtn.setPrefWidth(120);
        resetBtn.setOnAction(e -> resetPuzzle());


        Button solveBtn = new Button("Solve");
        solveBtn.setPrefWidth(120);
        solveBtn.setOnAction(e -> solvePuzzle());

        newGameBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");

        resetBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");

        solveBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        buttonBox.getChildren().addAll(newGameBtn, resetBtn, solveBtn);

        return buttonBox;
    }

    /**
     * Handle cell input with validation

     *  Only allow digits 1-9
     * Check if input violates Sudoku rules
     * Color feedback: GREEN = valid, RED = invalid

     * DSA CONCEPT: Constraint checking

     */
    private void handleCellInput(int row, int col, String newValue) {
        TextField cell = cells[row][col];

        // Calculate border widths for this cell
        String topBorder = (row % 3 == 0) ? "2 " : "1 ";
        String rightBorder = (col % 3 == 2) ? "2 " : "1 ";
        String bottomBorder = (row == 8) ? "2 " : "1 ";
        String leftBorder = (col % 3 == 0) ? "2" : "1";
        String borderStyle = "-fx-border-color: black; -fx-border-width: " +
                topBorder + rightBorder + bottomBorder + leftBorder + "; ";

        // Check if cell is editable (not pre-filled)
        if (cell.isEditable()) {

            // Only allow single digit 1-9 or empty
            if (newValue.length() > 1) {
                cell.setText(newValue.substring(0, 1));
                return;
            }

            if (newValue.isEmpty()) {
                board[row][col] = 0;
                cell.setStyle(borderStyle + "-fx-text-fill: black; -fx-font-size: 18px; -fx-background-color: white;");
                checkPuzzleCompletion();
                return;
            }

            // Validate input: must be digit 1-9
            if (!newValue.matches("[1-9]")) {
                cell.setText("");
                return;
            }

            // Convert to integer
            int num = Integer.parseInt(newValue);
            board[row][col] = num;


            // Validate using Sudoku rules
            if (isValidPlacement(row, col, num)) {
                // Valid placement - GREEN text
                cell.setStyle(borderStyle + "-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 18px; -fx-background-color: white;");
            } else {
                // Invalid placement - RED text
                cell.setStyle(borderStyle + "-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 18px; -fx-background-color: white;");
            }

            // Check if puzzle is completed
            checkPuzzleCompletion();
        }
    }

    /**
     * Validate if placing 'num' at [row][col] is valid
     */
    private boolean isValidPlacement(int row, int col, int num) {


        for (int c = 0; c < 9; c++) {
            if (c != col && board[row][c] == num) {
                return false;
            }
        }


        for (int r = 0; r < 9; r++) {
            if (r != row && board[r][col] == num) {
                return false;
            }
        }

        // Check 3x3 subgrid - same number shouldn't exist in same box
        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if ((r != row || c != col) && board[r][c] == num) {
                    return false;
                }
            }
        }

        return true; // All constraints satisfied
    }

    /**
     * Check if puzzle is completely solved
     * Display Puzzle Solved message if complete
     */
    private void checkPuzzleCompletion() {

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    solvedOverlay.setVisible(false);
                    return; // Puzzle not complete
                }
            }
        }

        // Check if all filled cells are valid (no red cells)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!isValidPlacement(row, col, board[row][col])) {
                    solvedOverlay.setVisible(false);
                    return; // Puzzle has errors
                }
            }
        }

        // Puzzle is complete and valid
        solvedOverlay.setVisible(true);
    }

    /**
     * Generate a new random puzzle
     */
    private void generateNewPuzzle() {
        // Hide solved message
        solvedOverlay.setVisible(false);

        // Generate new puzzle
        int[][] newPuzzle = generator.generatePuzzle();

        // Copy to board and initialBoard (DSA: 2D Array copying)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col] = newPuzzle[row][col];
                initialBoard[row][col] = newPuzzle[row][col];
            }
        }

        // Update UI
        updateGridFromBoard();
    }

    /**
     * Reset puzzle to initial state
     * Clears all user inputs, keeps original puzzle
     */
    private void resetPuzzle() {
        // Hide solved message
        solvedOverlay.setVisible(false);

        // Restore initial board (DSA: 2D Array copying)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col] = initialBoard[row][col];
            }
        }

        // Update UI
        updateGridFromBoard();
    }

    /**
     * Solve the puzzle using backtracking algorithm
     */
    private void solvePuzzle() {
        // Copy current board for solving
        int[][] tempBoard = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                tempBoard[row][col] = board[row][col];
            }
        }

        // Solve using backtracking
        if (solver.solve(tempBoard)) {
            // Solution found - update board
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    board[row][col] = tempBoard[row][col];
                }
            }

            // Update UI
            updateGridFromBoard();

            // Show solved message
            solvedOverlay.setVisible(true);
        }
    }

    /**
     * Update UI grid from internal board state
     */
    private void updateGridFromBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cell = cells[row][col];

                // Calculate border widths for this cell
                String topBorder = (row % 3 == 0) ? "2 " : "1 ";
                String rightBorder = (col % 3 == 2) ? "2 " : "1 ";
                String bottomBorder = (row == 8) ? "2 " : "1 ";
                String leftBorder = (col % 3 == 0) ? "2" : "1";
                String borderStyle = "-fx-border-color: black; -fx-border-width: " +
                        topBorder + rightBorder + bottomBorder + leftBorder + "; ";

                if (initialBoard[row][col] != 0) {

                    cell.setText(String.valueOf(board[row][col]));
                    cell.setEditable(false);
                    cell.setStyle(borderStyle + "-fx-background-color: lightgray; -fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 18px;");
                } else {

                    if (board[row][col] == 0) {
                        cell.setText("");
                    } else {
                        cell.setText(String.valueOf(board[row][col]));
                    }
                    cell.setEditable(true);
                    cell.setStyle(borderStyle + "-fx-background-color: white; -fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 18px;");
                }
            }
        }
    }


    public void show() {
        stage.show();
    }
}