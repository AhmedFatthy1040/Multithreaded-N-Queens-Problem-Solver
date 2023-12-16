package com.example.nqueenssolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NQueensSolverGUI extends JFrame {
    private JTextField numberOfQueensField;
    private JButton solveButton;

    public NQueensSolverGUI() {
        setTitle("N-Queens Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);

        numberOfQueensField = new JTextField(10);
        solveButton = new JButton("Solve");

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSolving();
            }
        });

        setLayout(new FlowLayout());
        add(new JLabel("Enter the number of queens:"));
        add(numberOfQueensField);
        add(solveButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startSolving() {
        int numberOfQueens = Integer.parseInt(numberOfQueensField.getText());

        for (int queenNumber = 1; queenNumber <= numberOfQueens; queenNumber++) {
            solveNQueensForThread(queenNumber, numberOfQueens);
        }
    }

    private void solveNQueensForThread(int threadNumber, int numberOfQueens) {
        new NQueensThread(threadNumber, numberOfQueens).start();
    }
}
