package com.example.nqueenssolver.gui;

import com.example.nqueenssolver.solver.NQueensThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NQueensSolverGUI extends JFrame {
    private static int offsetX = 0;  
    private static int offsetY = 0;
    private static final int WINDOW_SPACING = 420;
    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    
    private final List<NQueensThread> activeThreads = new ArrayList<>();
    private final List<JFrame> solutionFrames = new ArrayList<>();
    private ExecutorService executorService;
    
    private JTextField textField;
    private JButton solveButton;
    private JButton stopButton;
    private JLabel statusLabel;
    private JSpinner threadCountSpinner;

    public NQueensSolverGUI() {
        initComponents();
        setupWindowListener();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("N-Queens Solver - Multithreaded");
        setLayout(new BorderLayout());

        // Create main panel
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        // Create status panel
        JPanel statusPanel = createStatusPanel();
        add(statusPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        JLabel titleLabel = new JLabel("N-Queens Problem Solver");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(titleLabel, gbc);

        // Board size input
        gbc.gridwidth = 1; gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Board size (N):"), gbc);
        
        textField = new JTextField("8", 10);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(textField, gbc);

        // Thread count input
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Number of threads:"), gbc);
        
        threadCountSpinner = new JSpinner(new SpinnerNumberModel(
            Math.min(4, MAX_THREADS), 1, MAX_THREADS, 1));
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(threadCountSpinner, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        solveButton = new JButton("Start Solving");
        stopButton = new JButton("Stop All");
        stopButton.setEnabled(false);
        
        solveButton.addActionListener(this::onSolveButtonClicked);
        stopButton.addActionListener(this::onStopButtonClicked);
        
        buttonPanel.add(solveButton);
        buttonPanel.add(stopButton);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        statusLabel = new JLabel("Ready to solve N-Queens problem");
        panel.add(statusLabel);
        
        return panel;
    }

    private void setupWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cleanup();
            }
        });
    }

    private void onSolveButtonClicked(ActionEvent e) {
        try {
            int n = Integer.parseInt(textField.getText().trim());
            int threadCount = (Integer) threadCountSpinner.getValue();

            if (n <= 0) {
                showErrorMessage("Please enter a positive integer for the board size.");
                return;
            }

            if (n > 20) {
                int result = JOptionPane.showConfirmDialog(this,
                    "Large board sizes (>20) may take very long to solve. Continue?",
                    "Warning", JOptionPane.YES_NO_OPTION);
                if (result != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            solveNQueens(n, threadCount);
            
        } catch (NumberFormatException ex) {
            showErrorMessage("Please enter a valid integer for the board size.");
        }
    }

    private void onStopButtonClicked(ActionEvent e) {
        stopAllThreads();
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }

    private void solveNQueens(int n, int threadCount) {
        stopAllThreads();
        closeSolutionFrames();
        resetWindowPositions();

        solveButton.setEnabled(false);
        stopButton.setEnabled(true);
        statusLabel.setText("Solving " + n + "-Queens problem with " + threadCount + " threads...");

        executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            NQueensThread thread = new NQueensThread(i + 1, n, this::onThreadMessage);
            activeThreads.add(thread);
            
            addChessboardPanelToGUI(thread.getChessboardPanel(), thread.getThreadNumber());
            
            executorService.submit(thread);
        }
    }

    private void onThreadMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(message);
            
            boolean allFinished = activeThreads.stream().allMatch(t -> !t.isAlive());
            if (allFinished) {
                solveButton.setEnabled(true);
                stopButton.setEnabled(false);
                
                long solutionsFound = activeThreads.stream()
                    .mapToLong(t -> t.isSolutionFound() ? 1 : 0)
                    .sum();
                
                statusLabel.setText("Completed. " + solutionsFound + " solution(s) found out of " 
                    + activeThreads.size() + " threads.");
            }
        });
    }

    private void stopAllThreads() {
        if (executorService != null && !executorService.isShutdown()) {
            activeThreads.forEach(NQueensThread::requestStop);
            
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        
        activeThreads.clear();
        solveButton.setEnabled(true);
        stopButton.setEnabled(false);
        statusLabel.setText("Stopped all solving threads.");
    }

    private void closeSolutionFrames() {
        solutionFrames.forEach(JFrame::dispose);
        solutionFrames.clear();
    }

    private void resetWindowPositions() {
        offsetX = 0;
        offsetY = 0;
    }

    private void addChessboardPanelToGUI(NQueensThread.ChessboardPanel chessboardPanel, int threadNumber) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Thread " + threadNumber + " - N-Queens Solution");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setResizable(false);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Rectangle screenBounds = ge.getMaximumWindowBounds();
            
            if (offsetX + WINDOW_SPACING > screenBounds.width) {
                offsetX = 0;
                offsetY += WINDOW_SPACING;
            }
            
            if (offsetY + WINDOW_SPACING > screenBounds.height) {
                offsetY = 0;
            }

            frame.setLocation(offsetX + 50, offsetY + 50);
            offsetX += WINDOW_SPACING;

            frame.add(chessboardPanel);
            frame.setVisible(true);
            
            solutionFrames.add(frame);
        });
    }

    private void cleanup() {
        stopAllThreads();
        closeSolutionFrames();
    }
}