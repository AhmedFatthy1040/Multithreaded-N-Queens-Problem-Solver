package com.example.nqueenssolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NQueensSolverGUI extends JFrame {

    public NQueensSolverGUI() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("N-Queens Solver");

        JLabel label = new JLabel("Enter the number of queens (n):");
        JTextField textField = new JTextField(10);
        JButton solveButton = new JButton("Solve");

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(textField.getText());

                    // Ensure n is a valid value
                    if (n > 0) {
                        solveNQueens(n);
                    } else {
                        JOptionPane.showMessageDialog(NQueensSolverGUI.this,
                                "Please enter a positive integer for the number of queens.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(NQueensSolverGUI.this,
                            "Please enter a valid integer for the number of queens.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(label);
        panel.add(textField);
        panel.add(solveButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void solveNQueens(int n) {
        for (int i = 0; i < n; i++) {
            // Create a new thread for each solver
            NQueensThread thread = new NQueensThread(i, n);
            thread.start();
        }
    }
}
