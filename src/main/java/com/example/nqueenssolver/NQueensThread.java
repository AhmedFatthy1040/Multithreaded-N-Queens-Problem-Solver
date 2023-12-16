package com.example.nqueenssolver;

import javax.swing.*;
import java.util.Random;

public class NQueensThread extends Thread {
    private int threadNumber;
    private int n;

    public NQueensThread(int threadNumber, int n) {
        this.threadNumber = threadNumber;
        this.n = n;
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadNumber + " started.");

        // Solve N-Queens problem for this thread
        NQueensSolver solver = new NQueensSolver(n, new Random(System.currentTimeMillis() + threadNumber));
        boolean solved = solver.solve();

        if (solved) {
            int[][] chessboard = solver.getChessboard();
            String solutionMessage = "Thread " + threadNumber + " solved N-Queens:\n" + arrayToString(chessboard);

            // Show solution in a message dialog
            JOptionPane.showMessageDialog(null, solutionMessage, "Solution Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("Thread " + threadNumber + " couldn't find a solution for N-Queens.");
        }

        System.out.println("Thread " + threadNumber + " finished.");
    }

    private String arrayToString(int[][] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                sb.append(array[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
