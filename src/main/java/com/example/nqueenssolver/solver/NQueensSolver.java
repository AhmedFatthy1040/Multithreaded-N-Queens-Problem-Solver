package com.example.nqueenssolver.solver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class NQueensSolver {

    private final int[] queens;
    private final int n;
    private final Random random;

    public NQueensSolver(int n, Random random) {
        if (n <= 0) {
            throw new IllegalArgumentException("Board size must be positive");
        }
        this.n = n;
        this.queens = new int[n];
        this.random = random != null ? random : new Random();

        Arrays.fill(queens, -1);
    }

    public boolean solve() {
        return solveNQueens(0);
    }

    private boolean solveNQueens(int row) {
        if (row == n) {
            return true;
        }

        List<Integer> shuffledColumns = getShuffledColumns();

        for (int col : shuffledColumns) {
            if (isSafe(row, col)) {
                queens[row] = col;

                if (solveNQueens(row + 1)) {
                    return true;
                }

                queens[row] = -1;
            }
        }

        return false;
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == col || Math.abs(queens[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> getShuffledColumns() {
        List<Integer> columns = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            columns.add(i);
        }
        Collections.shuffle(columns, random);
        return columns;
    }

    public int[] getQueens() {
        return queens.clone();
    }

    public int[][] getChessboard() {
        int[][] chessboard = new int[n][n];
        for (int i = 0; i < n; i++) {
            int queenCol = queens[i];
            if (queenCol != -1) {
                chessboard[i][queenCol] = 1;
            }
        }
        return chessboard;
    }

    public void placeQueen(int row, int col) {
        if (row >= 0 && row < n && col >= 0 && col < n) {
            queens[row] = col;
        } else {
            throw new IllegalArgumentException("Invalid position: row=" + row + ", col=" + col);
        }
    }

    public void removeQueen(int row) {
        if (row >= 0 && row < n) {
            queens[row] = -1;
        } else {
            throw new IllegalArgumentException("Invalid row: " + row);
        }
    }

    public int getSize() {
        return n;
    }

    public void reset() {
        Arrays.fill(queens, -1);
    }
}