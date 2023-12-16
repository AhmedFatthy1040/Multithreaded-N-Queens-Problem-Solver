# N-Queens Solver Documentation

## Table of Contents
1. [Introduction](#introduction)
2. [Project Overview](#project-overview)
3. [Project Structure](#project-structure)
4. [How to Run](#how-to-run)
5. [Algorithm Details](#algorithm-details)
6. [Concurrency](#concurrency)
7. [Graphical User Interface (GUI)](#graphical-user-interface-gui)
8. [Dependencies](#dependencies)
9. [Performance Considerations](#performance-considerations)
10. [Future Improvements](#future-improvements)
11. [Contributing](#contributing)
12. [License](#license)

## 1. Introduction
The N-Queens Solver is a Java application that aims to find solutions to the N-Queens problem. The N-Queens problem involves placing N queens on an N×N chessboard in such a way that no two queens threaten each other. The project utilizes a multithreaded approach for solving the problem and includes a graphical user interface (GUI) to visualize the solutions.

## 2. Project Overview
- **NQueensSolver.java**: Implements the core logic for solving the N-Queens problem.
- **NQueensThread.java**: Represents a thread for solving the problem concurrently.
- **NQueensSolverGUI.java**: Provides a graphical user interface for interacting with the solver.
- **Main.java**: The main entry point for running the application.
- **pom.xml**: Maven configuration for managing dependencies.

## 3. Project Structure
The project follows a modular structure:

- com.example.nqueenssolver
  - Main.java
  - solver
    - NQueensSolver.java
    - NQueensThread.java
  - gui
    - NQueensSolverGUI.java


## 4. How to Run
1. Ensure you have Java installed.
2. Clone the repository: `git clone <repository-url>`

## 5. Algorithm Details
The algorithm uses a backtracking approach to explore all possible combinations of queen placements. The time complexity is \(O(N!)\), and the space complexity is \(O(N)\).

## 6. Concurrency
The project utilizes multithreading to solve the N-Queens problem concurrently. Each thread represents a unique attempt to find a solution.

## 7. Graphical User Interface (GUI)
The GUI, implemented using Java Swing, allows users to input the size of the chessboard (N) and visualize the solutions found by each thread.

## 8. Dependencies
- Java Swing for GUI components.

## 9. Performance Considerations
The actual runtime may vary based on the hardware and environment. Consider adjusting thread count and other parameters for optimal performance.

## 10. Future Improvements
- Implement additional heuristics for faster pruning of the search space.
- Enhance GUI features for better user interaction.

## 11. Contributing
Contributions are welcome! Feel free to fork the project and submit pull requests.

## 12. License
This project is licensed under the [MIT License](LICENSE).


