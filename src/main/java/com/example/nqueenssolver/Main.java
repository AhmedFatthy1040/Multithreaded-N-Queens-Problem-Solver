package com.example.nqueenssolver;
public class Main {

    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure GUI is created on the event dispatch thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create an instance of NQueensSolverGUI
                NQueensSolverGUI gui = new NQueensSolverGUI();
                gui.setVisible(true);
            }
        });
    }
}
