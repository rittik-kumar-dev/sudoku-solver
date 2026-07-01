package com.example.sudokugame;
/*
 * Generates random Sudoku puzzles using safe shuffling approach

 * DSA Concepts:
 * - 2D Arrays
 * - Randomization
 * - Array manipulation

 */

import java.util.Random;

public class SudokuGenerator {

    private Random random;

    public SudokuGenerator() {
        this.random = new Random();
    }

    /*
     * Generate a new random Sudoku puzzle

     */
    public int[][] generatePuzzle() {
        // Start with a valid completed board
        int[][] board = getBaseSudoku();

        // Shuffle to create randomness
        shuffleBoard(board);


        removeCells(board);

        return board;
    }

    /*
     * Get a base valid Sudoku solution
     */
    private int[][] getBaseSudoku() {
        // A valid completed Sudoku board (template)
        return new int[][] {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
    }

    /*
     * Shuffle the board to create variation

     */
    private void shuffleBoard(int[][] board) {

        // Shuffle rows within each 3-row block
        for (int block = 0; block < 3; block++) {
            int startRow = block * 3;

            // Perform multiple random swaps within this block
            for (int i = 0; i < 5; i++) {
                int row1 = startRow + random.nextInt(3);
                int row2 = startRow + random.nextInt(3);
                swapRows(board, row1, row2);
            }
        }

        // Shuffle columns within each 3-column block
        for (int block = 0; block < 3; block++) {
            int startCol = block * 3;

            // Perform multiple random swaps within this block
            for (int i = 0; i < 5; i++) {
                int col1 = startCol + random.nextInt(3);
                int col2 = startCol + random.nextInt(3);
                swapColumns(board, col1, col2);
            }
        }
    }

    /**
     * Swap two rows in the board
     * DSA CONCEPT: Array manipulation

     */
    private void swapRows(int[][] board, int row1, int row2) {
        int[] temp = board[row1];
        board[row1] = board[row2];
        board[row2] = temp;
    }

    /*
     * Swap two columns in the board
     */
    private void swapColumns(int[][] board, int col1, int col2) {
        for (int row = 0; row < 9; row++) {
            int temp = board[row][col1];
            board[row][col1] = board[row][col2];
            board[row][col2] = temp;
        }
    }

    /**
     * Remove cells randomly to create puzzle
     */
    private void removeCells(int[][] board) {
        // Number of cells to remove
        int cellsToRemove = 20 + random.nextInt(11); // 20 to 30

        int removed = 0;
        while (removed < cellsToRemove) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            // Only remove if not already empty
            if (board[row][col] != 0) {
                board[row][col] = 0;
                removed++;
            }
        }
    }
}