package com.example.nqueenssolver.utils;

import java.util.concurrent.TimeUnit;

public class PerformanceUtils {
    
    public static class Timer {
        private long startTime;
        private long endTime;
        private boolean running = false;
        
        public void start() {
            startTime = System.nanoTime();
            running = true;
        }
        
        public void stop() {
            endTime = System.nanoTime();
            running = false;
        }
        
        public long getElapsedNanos() {
            if (running) {
                return System.nanoTime() - startTime;
            }
            return endTime - startTime;
        }
        
        public long getElapsedMillis() {
            return TimeUnit.NANOSECONDS.toMillis(getElapsedNanos());
        }
        
        public long getElapsedSeconds() {
            return TimeUnit.NANOSECONDS.toSeconds(getElapsedNanos());
        }
        
        public String getFormattedTime() {
            long nanos = getElapsedNanos();
            long millis = TimeUnit.NANOSECONDS.toMillis(nanos);
            long seconds = TimeUnit.NANOSECONDS.toSeconds(nanos);
            
            if (seconds > 0) {
                return String.format("%.2f seconds", seconds + (millis % 1000) / 1000.0);
            } else if (millis > 0) {
                return String.format("%d ms", millis);
            } else {
                return String.format("%.2f μs", nanos / 1000.0);
            }
        }
    }
    
    public static int calculateOptimalThreadCount(int problemSize) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        
        if (problemSize <= 4) {
            return Math.min(2, availableProcessors);
        } else if (problemSize <= 8) {
            return Math.min(4, availableProcessors);
        } else {
            return Math.min(availableProcessors, Math.max(4, problemSize / 2));
        }
    }
    
    public static String getComplexityEstimate(int n) {
        if (n <= 4) {
            return "Very Easy (< 1 second)";
        } else if (n <= 8) {
            return "Easy (1-10 seconds)";
        } else if (n <= 12) {
            return "Medium (10 seconds - 2 minutes)";
        } else if (n <= 16) {
            return "Hard (2-30 minutes)";
        } else {
            return "Very Hard (may take hours)";
        }
    }
    
    public static String getSystemInfo() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / (1024 * 1024);
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        int processors = runtime.availableProcessors();
        
        return String.format(
            "System Info: %d processors, %d MB max memory, %d MB total, %d MB free",
            processors, maxMemory, totalMemory, freeMemory
        );
    }
}