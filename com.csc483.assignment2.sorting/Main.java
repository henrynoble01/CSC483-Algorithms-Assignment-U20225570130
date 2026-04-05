package com.csc483.assignment2.sorting;

import java.io.*;
import java.util.*;

/**
 * Main class illustrating different sorting algorithms and benchmarks.
 */
public class Main {

    static

/**
 * class 
 */
class Metrics {
        long comparisons = 0;
        long swaps = 0;
        long timeNs = 0;
    }

    interface Sorter {
        String name();
        Metrics sort(int[] array);
    }

    static

/**
 * class 
 */
class InsertionSort implements Sorter {
        public String name() { return "Insertion Sort"; }
        public Metrics sort(int[] arr) {
            Metrics m = new Metrics();
            long start = System.nanoTime();
            for (int i = 1; i < arr.length; i++) {
                int key = arr[i];
                int j = i - 1;
                while (j >= 0) {
                    m.comparisons++;
                    if (arr[j] > key) {
                        arr[j + 1] = arr[j];
                        m.swaps++; // Treating shift as assignment/swap
                        j = j - 1;
                    } else {
                        break;
                    }
                }
                arr[j + 1] = key;
                m.swaps++;
            }
            m.timeNs = System.nanoTime() - start;
            return m;
        }
    }

    static

/**
 * class 
 */
class MergeSort implements Sorter {
        public String name() { return "Merge Sort"; }
        public Metrics sort(int[] arr) {
            Metrics m = new Metrics();
            long start = System.nanoTime();
            int[] temp = new int[arr.length];
            mergeSort(arr, temp, 0, arr.length - 1, m);
            m.timeNs = System.nanoTime() - start;
            return m;
        }
        private void mergeSort(int[] arr, int[] temp, int left, int right, Metrics m) {
            if (left < right) {
                int center = (left + right) / 2;
                mergeSort(arr, temp, left, center, m);
                mergeSort(arr, temp, center + 1, right, m);
                merge(arr, temp, left, center + 1, right, m);
            }
        }
        private void merge(int[] arr, int[] temp, int leftPos, int rightPos, int rightEnd, Metrics m) {
            int leftEnd = rightPos - 1;
            int tempPos = leftPos;
            int numElements = rightEnd - leftPos + 1;

            while (leftPos <= leftEnd && rightPos <= rightEnd) {
                m.comparisons++;
                if (arr[leftPos] <= arr[rightPos]) {
                    temp[tempPos++] = arr[leftPos++];
                    m.swaps++;
                } else {
                    temp[tempPos++] = arr[rightPos++];
                    m.swaps++;
                }
            }
            while (leftPos <= leftEnd) { temp[tempPos++] = arr[leftPos++]; m.swaps++; }
            while (rightPos <= rightEnd) { temp[tempPos++] = arr[rightPos++]; m.swaps++; }
            for (int i = 0; i < numElements; i++, rightEnd--) {
                arr[rightEnd] = temp[rightEnd];
                m.swaps++;
            }
        }
    }

    static

/**
 * class 
 */
class QuickSort implements Sorter {
        public String name() { return "Quick Sort"; }
        public Metrics sort(int[] arr) {
            Metrics m = new Metrics();
            long start = System.nanoTime();
            quickSort(arr, 0, arr.length - 1, m);
            m.timeNs = System.nanoTime() - start;
            return m;
        }
        private void quickSort(int[] arr, int low, int high, Metrics m) {
            if (low < high) {
                int pi = partition(arr, low, high, m);
                quickSort(arr, low, pi - 1, m);
                quickSort(arr, pi + 1, high, m);
            }
        }
        private int partition(int[] arr, int low, int high, Metrics m) {
            // Use middle element as pivot to avoid stack overflow on already sorted arrays
            int mid = low + (high - low) / 2;
            int p = arr[mid];
            arr[mid] = arr[high];
            arr[high] = p;
            m.swaps++;
            
            int pivot = arr[high];
            int i = (low - 1);
            for (int j = low; j < high; j++) {
                m.comparisons++;
                if (arr[j] <= pivot) {
                    i++;
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    m.swaps++;
                }
            }
            int temp = arr[i + 1];
            arr[i + 1] = arr[high];
            arr[high] = temp;
            m.swaps++;
            return i + 1;
        }
    }

    public static void main(String[] args) throws IOException {
        int[] sizes = {100, 1000, 10000, 100000};
        String[] types = {"Random", "Sorted", "ReverseSorted", "NearlySorted", "FewUnique"};
        Sorter[] sorters = {new InsertionSort(), new MergeSort(), new QuickSort()};
        int runs = 5;

        List<double[]> insertionTimes = new ArrayList<>();
        List<double[]> mergeTimes = new ArrayList<>();
        
        for (String type : types) {
            System.out.println("================================================================================");
            System.out.println("SORTING ALGORITHMS COMPARISON - " + type.toUpperCase() + " DATA");
            System.out.println("================================================================================");
            System.out.println();
            System.out.printf("%-15s %-15s %-15s %-15s %-15s\n", "Input Size", "Algorithm", "Time (ms)", "Comparisons", "Swaps");
            
            for (int size : sizes) {
                int[] baseArray = generateArray(size, type);
                saveDataset(baseArray, type, size);

                for (Sorter sorter : sorters) {
                    long totalComps = 0, totalSwaps = 0;
                    double[] timesMs = new double[runs];
                    
                    for (int r = 0; r < runs; r++) {
                        int[] arrCopy = Arrays.copyOf(baseArray, baseArray.length);
                        Metrics m = sorter.sort(arrCopy);
                        totalComps += m.comparisons;
                        totalSwaps += m.swaps;
                        timesMs[r] = m.timeNs / 1_000_000.0;
                    }
                    
                    double meanTime = calculateMean(timesMs);
                    double stdDev = calculateStdDev(timesMs, meanTime);
                    long avgComps = totalComps / runs;
                    long avgSwaps = totalSwaps / runs;
                    
                    String algoName = sorter.name().replace(" Sort", "");
                    String swapsStr = sorter.name().equals("Merge Sort") ? "N/A" : String.format("%,d", avgSwaps);
                    
                    System.out.printf("%,-15d %-15s %-15.2f %,-15d %-15s\n",
                        size, algoName, meanTime, avgComps, swapsStr);
                        
                    if (size == 10000 && type.equals("Random")) {
                        if (sorter.name().equals("Insertion Sort")) insertionTimes.add(timesMs);
                        if (sorter.name().equals("Merge Sort")) mergeTimes.add(timesMs);
                    }
                }
            }
            System.out.println();
        }
        
        System.out.println("\n--- Statistical Analysis (T-Test) ---");
        System.out.println("Comparing Insertion Sort vs Merge Sort for Size 10000 (Random Array)");
        if (!insertionTimes.isEmpty() && !mergeTimes.isEmpty()) {
            double tStat = calculateTStatistic(insertionTimes.get(0), mergeTimes.get(0));
            System.out.printf("T-Statistic: %.4f%n", tStat);
            System.out.println("A large absolute t-value (> 2.306 for df=8) indicates a statistically significant difference in performance.");
        }
    }

    private static int[] generateArray(int size, String type) {
        int[] arr = new int[size];
        Random rand = new Random(42);
        switch (type) {
            case "Random":
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt(size);
                break;
            case "Sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;
            case "ReverseSorted":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;
            case "NearlySorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                int swaps = (int)(size * 0.10);
                for (int i = 0; i < swaps; i++) {
                    int idx1 = rand.nextInt(size);
                    int idx2 = rand.nextInt(size);
                    int temp = arr[idx1];
                    arr[idx1] = arr[idx2];
                    arr[idx2] = temp;
                }
                break;
            case "FewUnique":
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt(10);
                break;
        }
        return arr;
    }

    private static void saveDataset(int[] arr, String type, int size) {
        // Resolve dataset dir relative to the JVM working directory (project root)
        String projectRoot = System.getProperty("user.dir");
        File datasetDir = new File(projectRoot, "dataset");
        if (!datasetDir.exists() && !datasetDir.mkdirs()) {
            System.err.println("Failed to create dataset directory: " + datasetDir.getAbsolutePath());
            return;
        }
        File outFile = new File(datasetDir, "dataset_" + type + "_" + size + ".csv");
        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile))) {
            for (int i = 0; i < arr.length; i++) {
                writer.print(arr[i]);
                if (i < arr.length - 1) writer.print(",");
            }
            writer.println();
        } catch (IOException e) {
            System.err.println("Failed to write dataset " + type + "_" + size + ": " + e.getMessage());
        }
    }

    private static double calculateMean(double[] data) {
        double sum = 0.0;
        for (double a : data) sum += a;
        return sum / data.length;
    }

    private static double calculateStdDev(double[] data, double mean) {
        double temp = 0;
        for (double a : data) temp += (a - mean) * (a - mean);
        return Math.sqrt(temp / (data.length - 1));
    }

    private static double calculateTStatistic(double[] sample1, double[] sample2) {
        double mean1 = calculateMean(sample1);
        double mean2 = calculateMean(sample2);
        double var1 = Math.pow(calculateStdDev(sample1, mean1), 2) / sample1.length;
        double var2 = Math.pow(calculateStdDev(sample2, mean2), 2) / sample2.length;
        return (mean1 - mean2) / Math.sqrt(var1 + var2);
    }
}
