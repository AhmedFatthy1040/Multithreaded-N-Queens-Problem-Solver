package com.example.nqueenssolver;

import javax.swing.*;
import java.awt.*;

public class NQueensThread extends Thread {
    private final int threadNumber;
    private final int n;

    public NQueensThread(int threadNumber, int n) {
        this.threadNumber = threadNumber;
        this.n = n;
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadNumber + " started.");

        // Solve N-Queens problem for this thread
        NQueensSolver solver = new NQueensSolver(n);
        boolean solved = solver.solve();

        if (solved) {
            // Display the solution in a chessboard window
            displayChessboard(solver.getQueens());
        } else {
            System.out.println("Thread " + threadNumber + " couldn't find a solution for N-Queens.");
        }

        System.out.println("Thread " + threadNumber + " finished.");
    }

    private void displayChessboard(int[] queens) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Thread " + threadNumber + " Solution");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 400);

            // Set location relative to the previous frame with increased spacing
            if (threadNumber > 0) {
                JFrame previousFrame = getPreviousFrame();
                int offsetX = previousFrame.getX() + 40;  // Increase the horizontal spacing
                int offsetY = previousFrame.getY() + 40;  // Increase the vertical spacing
                frame.setLocation(offsetX, offsetY);
            } else {
                frame.setLocationRelativeTo(null); // Center the first frame on the screen
            }

            ChessboardPanel chessboardPanel = new ChessboardPanel(queens);
            frame.add(chessboardPanel);

            frame.setVisible(true);
        });
    }

    private JFrame getPreviousFrame() {
        Frame[] frames = JFrame.getFrames();
        if (frames.length > threadNumber) {
            return (JFrame) frames[threadNumber - 1];
        }
        return null;
    }

    private static class ChessboardPanel extends JPanel {
        private final int[] queens;

        public ChessboardPanel(int[] queens) {
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