# N-Queens Solver - Multithreaded Implementation

## Table of Contents
1. [Introduction](#introduction)
2. [Features](#features)
3. [Project Structure](#project-structure)
4. [Requirements](#requirements)
5. [How to Build and Run](#how-to-build-and-run)
6. [Algorithm Details](#algorithm-details)
7. [Concurrency and Performance](#concurrency-and-performance)
8. [User Interface](#user-interface)
9. [Improvements Made](#improvements-made)
10. [Performance Considerations](#performance-considerations)
11. [Testing](#testing)
12. [Contributing](#contributing)
13. [License](#license)
14. [Media Resources](#media-resources)

## 1. Introduction

The N-Queens Solver is a modern Java application that solves the classical N-Queens problem using advanced multithreading techniques. The N-Queens problem involves placing N queens on an N×N chessboard such that no two queens threaten each other (no two queens share the same row, column, or diagonal).

This implementation features:
- **Modern Java 17** with enhanced performance and features
- **Thread-safe concurrent solving** with optimal thread management
- **Real-time visualization** of the solving process
- **Improved user interface** with better controls and feedback
- **Comprehensive testing** with JUnit 5
- **Performance monitoring** and optimization utilities

![queens](https://github.com/AhmedFatthy1040/Multithreaded-N-Queens-Problem-Solver/assets/91102592/0788a467-1379-4a9f-968e-2a82bd5db3a7)

## 2. Features

### Core Features
- ✅ **Multithreaded N-Queens solving** with configurable thread count
- ✅ **Real-time visualization** of the solving process
- ✅ **Thread-safe implementation** using modern concurrency patterns
- ✅ **Optimized backtracking algorithm** with randomization
- ✅ **Responsive GUI** with start/stop controls
- ✅ **Performance monitoring** and complexity estimation
- ✅ **Comprehensive error handling** and input validation
- ✅ **Unit tests** with high coverage

### Technical Improvements
- 🔄 **Upgraded to Java 17** from Java 8
- 🧵 **ExecutorService-based thread management** instead of raw threads
- 🎨 **Enhanced UI design** with better layout and controls
- 🛡️ **Thread safety** using AtomicBoolean and proper synchronization
- ⚡ **Performance optimizations** and memory management
- 📊 **Performance utilities** for monitoring and optimization
- 🧪 **Comprehensive testing** framework

## 3. Project Structure

```
src/
├── main/java/com/example/nqueenssolver/
│   ├── Main.java                    # Application entry point
│   ├── gui/
│   │   └── NQueensSolverGUI.java   # Enhanced GUI with modern controls
│   ├── solver/
│   │   ├── NQueensSolver.java      # Core solving algorithm (improved)
│   │   └── NQueensThread.java      # Thread implementation (rewritten)
│   └── utils/
│       └── PerformanceUtils.java   # Performance monitoring utilities
└── test/java/com/example/nqueenssolver/
    └── solver/
        └── NQueensSolverTest.java  # Comprehensive unit tests
```

## 4. Requirements

### System Requirements
- **Java 17** or higher
- **Maven 3.6+** for build management
- **Minimum 4GB RAM** recommended for larger problems (N > 15)
- **Multi-core processor** recommended for optimal performance

### Development Requirements
- **IDE** with Java 17 support (IntelliJ IDEA, Eclipse, VS Code)
- **JUnit 5** for running tests
- **Git** for version control

## 5. How to Build and Run

### Using Maven (Recommended)

1. **Clone the repository:**
   ```bash
   git clone https://github.com/AhmedFatthy1040/Multithreaded-N-Queens-Problem-Solver.git
   cd Multithreaded-N-Queens-Problem-Solver
   ```

2. **Build the project:**
   ```bash
   mvn clean compile
   ```

3. **Run the application:**
   ```bash
   mvn exec:java
   ```

4. **Run tests:**
   ```bash
   mvn test
   ```

5. **Create executable JAR:**
   ```bash
   mvn clean package
   java -jar target/nqueenssolver-1.0.0.jar
   ```

### Using IDE
1. Import the project as a Maven project
2. Ensure Java 17 is configured
3. Run `Main.java` from your IDE
4. Run tests from the test directory

## 6. Algorithm Details

### Core Algorithm
The solver uses an **optimized backtracking algorithm** with the following enhancements:

- **Randomized column ordering** to find different solutions across threads
- **Efficient conflict detection** using mathematical formulas
- **Early termination** when solutions are found
- **Memory-efficient state representation**

### Time and Space Complexity
- **Time Complexity:** O(N!) in the worst case, but typically much better due to pruning
- **Space Complexity:** O(N) for the recursion stack and queen positions
- **Practical Performance:** Significantly improved through multithreading and optimizations

### Pseudocode
```
function solveNQueens(row):
    if row == N:
        return true // Solution found
    
    for each column in randomized_order:
        if isSafe(row, column):
            placeQueen(row, column)
            if solveNQueens(row + 1):
                return true
            removeQueen(row)
    
    return false // No solution in this branch
```

## 7. Concurrency and Performance

### Thread Management
- **ExecutorService-based** thread pool management
- **Configurable thread count** with intelligent defaults
- **Proper thread lifecycle** management with cleanup
- **Thread-safe state** sharing and updates

### Performance Features
- **Optimal thread count calculation** based on system capabilities
- **Performance monitoring** with execution time tracking
- **Memory usage optimization** with proper object lifecycle
- **Complexity estimation** for user guidance

### Synchronization
- **AtomicBoolean** for thread-safe flags
- **SwingUtilities.invokeLater()** for UI updates
- **Proper thread interruption** handling
- **Resource cleanup** on application exit

## 8. User Interface

### Main Window Features
- **Board size input** with validation (1-20 recommended)
- **Thread count selection** with optimal defaults
- **Start/Stop controls** for solving process
- **Status updates** with real-time feedback
- **Error handling** with user-friendly messages

### Visualization Windows
- **Individual thread windows** showing solving progress
- **Real-time updates** of queen placements
- **Enhanced chessboard** with better colors and queen symbols
- **Automatic window positioning** with screen bounds handling

### User Experience Improvements
- **Input validation** prevents invalid configurations
- **Warning dialogs** for potentially long-running operations
- **Responsive controls** that update based on current state
- **Proper cleanup** when windows are closed

## 9. Improvements Made

### Code Quality
- ✅ **Java 17 upgrade** with modern language features
- ✅ **Comprehensive documentation** with JavaDoc
- ✅ **Error handling** and input validation
- ✅ **Code organization** with proper package structure
- ✅ **Performance optimizations** and memory management

### Thread Safety
- ✅ **AtomicBoolean** for thread-safe state management
- ✅ **ExecutorService** for proper thread pool management
- ✅ **Proper synchronization** for UI updates
- ✅ **Resource cleanup** and lifecycle management

### User Experience
- ✅ **Enhanced GUI** with better layout and controls
- ✅ **Real-time feedback** and status updates
- ✅ **Input validation** and error messages
- ✅ **Performance monitoring** and estimates
- ✅ **Responsive design** with proper window management

### Testing and Reliability
- ✅ **Unit tests** with JUnit 5
- ✅ **Edge case handling** and validation
- ✅ **Performance testing** utilities
- ✅ **Continuous integration** ready

## 10. Performance Considerations

### Optimal Settings
- **Board sizes 1-12:** Generally solve quickly (< 1 minute)
- **Board sizes 13-16:** May take several minutes
- **Board sizes 17+:** Can take hours or longer

### Thread Count Guidelines
- **Small problems (N ≤ 8):** 2-4 threads optimal
- **Medium problems (N 9-12):** 4-8 threads optimal  
- **Large problems (N ≥ 13):** Use all available CPU cores

### Memory Usage
- **Base memory:** ~50MB for the application
- **Per thread:** ~1-5MB depending on problem size
- **Large problems:** May require 1-4GB+ RAM

## 11. Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report

# Run specific test class
mvn test -Dtest=NQueensSolverTest
```

### Test Coverage
- **Unit tests** for core algorithm functionality
- **Edge case testing** for invalid inputs
- **Performance testing** utilities
- **Thread safety** validation

## 12. Contributing

Contributions are welcome! Please follow these guidelines:

1. **Fork the repository** and create a feature branch
2. **Follow Java coding standards** and maintain documentation
3. **Add tests** for new functionality
4. **Update README** if adding new features
5. **Submit a pull request** with clear description

### Development Setup
1. Ensure Java 17+ is installed
2. Import project into your IDE
3. Run tests to verify setup
4. Make changes and add tests
5. Ensure all tests pass before submitting

## 13. License

This project is licensed under the [MIT License](LICENSE).

## 14. Media Resources

### Video Demonstration
🎥 **Project explanation video (Arabic):** 
This video has been compressed. For better quality, check the resources folder.

https://github.com/AhmedFatthy1040/Multithreaded-N-Queens-Problem-Solver/assets/91102592/17245fce-954c-4411-9b6c-d660ced78c44

### Documentation
📄 **Detailed documentation:** [Documentation.pdf](https://github.com/AhmedFatthy1040/Multithreaded-N-Queens-Problem-Solver/files/13694411/Documention.pdf)

---

## Quick Start

1. **Clone and build:**
   ```bash
   git clone <repository-url>
   cd Multithreaded-N-Queens-Problem-Solver
   mvn clean compile
   ```

2. **Run the application:**
   ```bash
   mvn exec:java
   ```

3. **Start solving:**
   - Enter board size (try 8 for a quick demo)
   - Adjust thread count if desired
   - Click "Start Solving"
   - Watch the visualization windows!
