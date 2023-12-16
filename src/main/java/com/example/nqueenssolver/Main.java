package com.example.nqueenssolver;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NQueensSolverGUI gui = new NQueensSolverGUI();
            gui.setVisible(true);
        });
    }
}
