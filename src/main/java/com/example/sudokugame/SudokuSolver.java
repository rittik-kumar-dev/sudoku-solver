package com.example.sudokugame;
/*

 * Contains the core DSA logic - Backtracking Algorithm

 * DSA Concepts Used:
 * 1. 2D Arrays - for board representation
 * 2. Recursion - for exploring possible solutions
 * 3. Backtracking - for undoing wrong choices
 * 4. Constraint Checking - validating Sudoku rules

 */

public class SudokuSolver {

    /**
     * Main solving method using Backtracking Algorithm
     */
    public boolean solve(int[][] board) {
        // RECURSION + BACKTRACKING

        // Find the next empty cell
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                // If cell is empty
                if (board[row][col] == 0) {


                    for (int num = 1; num <= 9; num++) {


                        if (isSafe(board, row, col, num)) {


                            board[row][col] = num;


                            // Recursively solve the rest of the puzzle
                            if (solve(board)) {
                                return true; // Solution found
                            }


                            // If recursion fails, undo the choice
                            board[row][col] = 0;
                        }
                    }

                    // No valid number found, trigger backtracking
                    return false;
                }
            }
        }

        // No empty cells left - puzzle is solved
        return true;
    }

    /**
     * Check if placing 'num' at board[row][col] is valid
     */
    public boolean isSafe(int[][] board, int row, int col, int num) {


        // Check row constraint
        for (int c = 0; c < 9; c++) {
            if (board[row][c] == num) {
                return false; // Number already in row
            }
        }

        // Check column constraint
        for (int r = 0; r < 9; r++) {
            if (board[r][col] == num) {
                return false; // Number already in column
            }
        }

        // Check 3x3 subgrid constraint

        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (board[r][c] == num) {
                    return false; // Number already in 3x3 box
                }
            }
        }

        // All constraints satisfied
        return true;
    }
}