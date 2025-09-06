package com.example.nqueenssolver.solver;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class NQueensThread extends Thread {
    private final int threadNumber;
    private final int n;
    private final Random random;
    private final ChessboardPanel chessboardPanel;
    private final NQueensSolver solver;
    private final AtomicBoolean solutionFound = new AtomicBoolean(false);
    private final Consumer<String> messageCallback;
    private volatile boolean shouldStop = false;

    public NQueensThread(int threadNumber, int n, Consumer<String> messageCallback) {
        this.threadNumber = threadNumber;
        this.n = n;
        this.random = new Random(System.currentTimeMillis() + threadNumber);
        this.chessboardPanel = new ChessboardPanel(new int[n]);
        this.solver = new NQueensSolver(n, random);
        this.messageCallback = messageCallback;
        this.setName("NQueens-Thread-" + threadNumber);
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadNumber + " started.");

        try {
            // Solve N-Queens problem for this thread
            solveWithVisualization();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread " + threadNumber + " was interrupted.");
        } catch (Exception e) {
            System.err.println("Thread " + threadNumber + " encountered an error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Thread " + threadNumber + " finished.");
            
            if (solutionFound.get()) {
                showMessage("Thread " + threadNumber + " found a solution!");
            } else {
                showMessage("Thread " + threadNumber + " finished without finding a solution.");
            }
        }
    }

    private void showMessage(String message) {
        if (messageCallback != null) {
            SwingUtilities.invokeLater(() -> messageCallback.accept(message));
        }
    }

    private void solveWithVisualization() throws InterruptedException {
        solveNQueensWithVisualization(0);
    }

    public boolean isSolutionFound() {
        return solutionFound.get();
    }

    public void requestStop() {
        shouldStop = true;
        this.interrupt();
    }

    private void solveNQueensWithVisualization(int row) throws InterruptedException {
        if (shouldStop) {
            return;
        }

        if (row == n) {
            solutionFound.set(true);
            showMessage("Thread " + threadNumber + " found a solution!");
            return;
        }

        java.util.List<Integer> shuffledColumns = getShuffledColumns();

        for (int col : shuffledColumns) {
            if (shouldStop) {
                return;
            }

            if (isSafe(row, col)) {
                solver.placeQueen(row, col);

                updateChessboardDisplay(solver.getQueens().clone());
                Thread.sleep(300);

                solveNQueensWithVisualization(row + 1);

                if (solutionFound.get()) {
                    return;
                }

                solver.removeQueen(row);
            }
        }
    }

    private void updateChessboardDisplay(int[] queens) {
        SwingUtilities.invokeLater(() -> {
            chessboardPanel.updateQueens(queens);
            chessboardPanel.repaint();
        });
    }

    private java.util.List<Integer> getShuffledColumns() {
        java.util.List<Integer> columns = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            columns.add(i);
        }
        java.util.Collections.shuffle(columns, random);
        return columns;
    }

    private boolean isSafe(int row, int col) {
        int[] queens = solver.getQueens();
        for (int i = 0; i < row; i++) {
            if (queens[i] == col || Math.abs(queens[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    public ChessboardPanel getChessboardPanel() {
        return chessboardPanel;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public static class ChessboardPanel extends JPanel {
        private volatile int[] queens;
        private static final Color LIGHT_SQUARE = new Color(240, 217, 181);
        private static final Color DARK_SQUARE = new Color(181, 136, 99);
        private static final Color QUEEN_COLOR = new Color(220, 20, 20);
        private static final Color QUEEN_BORDER = Color.BLACK;

        public ChessboardPanel(int[] queens) {
            this.queens = queens != null ? queens.clone() : new int[0];
            setPreferredSize(new Dimension(400, 400));
        }

        public void updateQueens(int[] queens) {
            this.queens = queens != null ? queens.clone() : new int[0];
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (queens.length == 0) {
                return;
            }

            int size = queens.length;
            int cellSize = Math.min(getWidth(), getHeight()) / size;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if ((i + j) % 2 == 0) {
                        g2d.setColor(LIGHT_SQUARE);
                    } else {
                        g2d.setColor(DARK_SQUARE);
                    }
                    g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }

            for (int i = 0; i < size; i++) {
                int col = queens[i];
                if (col >= 0 && col < size) {
                    int x = col * cellSize;
                    int y = i * cellSize;
                    int margin = cellSize / 8;
                    
                    g2d.setColor(QUEEN_COLOR);
                    g2d.fillOval(x + margin, y + margin, cellSize - 2 * margin, cellSize - 2 * margin);
                    
                    g2d.setColor(QUEEN_BORDER);
                    g2d.setStroke(new BasicStroke(2.0f));
                    g2d.drawOval(x + margin, y + margin, cellSize - 2 * margin, cellSize - 2 * margin);
                    
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Arial", Font.BOLD, cellSize / 3));
                    FontMetrics fm = g2d.getFontMetrics();
                    String crown = "♕";
                    int textX = x + (cellSize - fm.stringWidth(crown)) / 2;
                    int textY = y + (cellSize + fm.getAscent()) / 2;
                    g2d.drawString(crown, textX, textY);
                }
            }

            g2d.dispose();
        }
    }
}