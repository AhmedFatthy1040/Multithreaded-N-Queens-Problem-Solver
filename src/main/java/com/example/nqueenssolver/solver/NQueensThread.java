package com.example.nqueenssolver.solver;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class NQueensThread extends SwingWorker<Void, int[]> {
    private final int threadNumber;
    private final int n;
    private final Random random;
    private final ChessboardPanel chessboardPanel; // Added ChessboardPanel instance
    private final NQueensSolver solver;

    public NQueensThread(int threadNumber, int n, Random random, ChessboardPanel chessboardPanel) {
        this.threadNumber = threadNumber;
        this.n = n;
        this.random = random;
        this.chessboardPanel = chessboardPanel;
        this.solver = new NQueensSolver(n, random);
    }
    @Override
    protected Void doInBackground() {
        System.out.println("Thread " + threadNumber + " started.");

        // Solve N-Queens problem for this thread
        solveWithVisualization();

        System.out.println("Thread " + threadNumber + " finished.");

        // Display a message indicating the thread has finished
        showMessage("Thread " + threadNumber + " has finished its task.");

        return null;
    }

    private void showMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, message, "Thread Finished", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void solveWithVisualization() {
        try {
            solveNQueensWithVisualization(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void solveNQueensWithVisualization(int row) throws InterruptedException {
        if (row == n) {
            // All queens are placed successfully
            return;
        }

        int[] shuffledColumns = getShuffledColumns();

        for (int i = 0; i < n; i++) {
            int col = shuffledColumns[i];
            if (isSafe(row, col)) {
                // Place the queen in this cell
                solver.placeQueen(row, col);

                // Publish intermediate state to update UI
                publish(solver.getQueens().clone());

                // Recur to place queens in the remaining rows
                solveNQueensWithVisualization(row + 1);

                // If placing queen in the current cell doesn't lead to a solution, backtrack
                solver.removeQueen(row);
                Thread.sleep(500);  // Adjust sleep duration for visualization speed
            }
        }
    }

    @Override
    protected void process(java.util.List<int[]> chunks) {
        int[] latestQueens = chunks.get(chunks.size() - 1);
        updateChessboardDisplay(latestQueens);
    }

    private void updateChessboardDisplay(int[] queens) {
        SwingUtilities.invokeLater(() -> {
            chessboardPanel.updateQueens(queens);
            chessboardPanel.repaint();
        });
    }

    private int[] getShuffledColumns() {
        int[] columns = new int[n];
        for (int i = 0; i < n; i++) {
            columns[i] = i;
        }

        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            // Swap columns[i] and columns[j]
            int temp = columns[i];
            columns[i] = columns[j];
            columns[j] = temp;
        }

        return columns;
    }

    private boolean isSafe(int row, int col) {
        // Check if no queens are in the same column or diagonals
        for (int i = 0; i < row; i++) {
            if (solver.getQueens()[i] == col || Math.abs(solver.getQueens()[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    public static class ChessboardPanel extends JPanel {
        private int[] queens;

        public ChessboardPanel(int[] queens) {
            this.queens = queens;
        }

        public void updateQueens(int[] queens) {
            this.queens = queens;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int size = queens.length;
            int cellSize = getWidth() / size;

            // Draw chessboard
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if ((i + j) % 2 == 0) {
                        g.setColor(Color.LIGHT_GRAY);
                    } else {
                        g.setColor(Color.DARK_GRAY);
                    }
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }

            // Draw queens
            g.setColor(Color.RED);
            for (int i = 0; i < size; i++) {
                int col = queens[i];
                g.fillOval(col * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }
}
