package com.example.nqueenssolver.solver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

public class NQueensSolverTest {
    
    private NQueensSolver solver;
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random(42);
    }

    @Test
    void testConstructorWithValidSize() {
        solver = new NQueensSolver(8, random);
        assertEquals(8, solver.getSize());
        assertNotNull(solver.getQueens());
        assertEquals(8, solver.getQueens().length);
    }

    @Test
    void testConstructorWithInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NQueensSolver(0, random);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new NQueensSolver(-1, random);
        });
    }

    @Test
    void testConstructorWithNullRandom() {
        solver = new NQueensSolver(4, null);
        assertNotNull(solver);
        assertEquals(4, solver.getSize());
    }

    @Test
    void testInitialState() {
        solver = new NQueensSolver(4, random);
        int[] queens = solver.getQueens();
        
        for (int pos : queens) {
            assertEquals(-1, pos, "All initial positions should be -1");
        }
    }

    @Test
    void testPlaceQueen() {
        solver = new NQueensSolver(4, random);
        solver.placeQueen(0, 1);
        
        int[] queens = solver.getQueens();
        assertEquals(1, queens[0]);
        assertEquals(-1, queens[1]);
        assertEquals(-1, queens[2]);
        assertEquals(-1, queens[3]);
    }

    @Test
    void testPlaceQueenInvalidPosition() {
        solver = new NQueensSolver(4, random);
        
        assertThrows(IllegalArgumentException.class, () -> {
            solver.placeQueen(-1, 0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            solver.placeQueen(0, -1);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            solver.placeQueen(4, 0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            solver.placeQueen(0, 4);
        });
    }

    @Test
    void testRemoveQueen() {
        solver = new NQueensSolver(4, random);
        solver.placeQueen(0, 1);
        solver.removeQueen(0);
        
        int[] queens = solver.getQueens();
        assertEquals(-1, queens[0]);
    }

    @Test
    void testRemoveQueenInvalidRow() {
        solver = new NQueensSolver(4, random);
        
        assertThrows(IllegalArgumentException.class, () -> {
            solver.removeQueen(-1);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            solver.removeQueen(4);
        });
    }

    @Test
    void testReset() {
        solver = new NQueensSolver(4, random);
        solver.placeQueen(0, 1);
        solver.placeQueen(1, 3);
        solver.reset();
        
        int[] queens = solver.getQueens();
        for (int pos : queens) {
            assertEquals(-1, pos);
        }
    }

    @Test
    void testGetChessboard() {
        solver = new NQueensSolver(4, random);
        solver.placeQueen(0, 1);
        solver.placeQueen(1, 3);
        
        int[][] board = solver.getChessboard();
        assertEquals(4, board.length);
        assertEquals(4, board[0].length);
        
        assertEquals(1, board[0][1]); // Queen at (0,1)
        assertEquals(1, board[1][3]); // Queen at (1,3)
        assertEquals(0, board[0][0]); // No queen at (0,0)
        assertEquals(0, board[1][0]); // No queen at (1,0)
    }

    @Test
    void testSolveSmallBoard() {
        solver = new NQueensSolver(4, random);
        boolean result = solver.solve();
        assertTrue(result, "4x4 board should have a solution");
        
        int[] queens = solver.getQueens();
        assertTrue(isValidSolution(queens), "Solution should be valid");
    }

    @Test
    void testSolveImpossibleBoard() {
        solver = new NQueensSolver(2, random);
        assertFalse(solver.solve(), "2x2 board should have no solution");
        
        solver = new NQueensSolver(3, random);
        assertFalse(solver.solve(), "3x3 board should have no solution");
    }

    @Test
    void testQueensArrayIsCopy() {
        solver = new NQueensSolver(4, random);
        solver.placeQueen(0, 1);
        
        int[] queens1 = solver.getQueens();
        int[] queens2 = solver.getQueens();
        
        assertNotSame(queens1, queens2, "getQueens() should return a copy");
        assertArrayEquals(queens1, queens2, "Copies should have same content");
        
        queens1[0] = 999;
        
        assertEquals(1, solver.getQueens()[0]);
    }

    private boolean isValidSolution(int[] queens) {
        int n = queens.length;
        
        for (int i = 0; i < n; i++) {
            if (queens[i] == -1) {
                return false;
            }
            
            for (int j = i + 1; j < n; j++) {
                if (queens[i] == queens[j]) {
                    return false;
                }
                
                if (Math.abs(queens[i] - queens[j]) == Math.abs(i - j)) {
                    return false;
                }
            }
        }
        
        return true;
    }
}