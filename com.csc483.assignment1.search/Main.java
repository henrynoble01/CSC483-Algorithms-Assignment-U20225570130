package com.csc483.assignment1.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Main driver class for testing search functionality.
 */
public class Main {
    
    // Sequential search by ID: O(N)
    /**
     * Performs sequential search by product ID.
     * @param products Array to search
     * @param targetId Target ID
     * @return Found product or null
     */
    /**
     * Performs sequential search by product ID.
     * @param products Array to search
     * @param targetId Target ID
     * @return Found product or null
     */
    public static Product sequentialSearchById(Product[] products, int targetId) {
        for (Product p : products) {
            if (p.getProductId() == targetId) {
                return p;
            }
        }
        return null;
    }

    // Binary search by ID: O(log N) (Assumes array is sorted)
    /**
     * Performs binary search by product ID. Assumes array is sorted.
     * @param products Sorted array to search
     * @param targetId Target ID
     * @return Found product or null
     */
    /**
     * Performs binary search by product ID. Assumes array is sorted.
     * @param products Sorted array to search
     * @param targetId Target ID
     * @return Found product or null
     */
    public static Product binarySearchById(Product[] products, int targetId) {
        int left = 0;
        int right = products.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (products[mid].getProductId() == targetId) {
                return products[mid];
            }
            if (products[mid].getProductId() < targetId) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    // Sequential search by Name: O(N)
    /**
     * Performs sequential search by product Name.
     * @param products Array to search
     * @param targetName Target item name
     * @return Found product or null
     */
    /**
     * Performs sequential search by product Name.
     * @param products Array to search
     * @param targetName Target item name
     * @return Found product or null
     */
    public static Product searchByName(Product[] products, String targetName) {
        for (Product p : products) {
            if (p.getProductName().equals(targetName)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Main method to benchmark searches.
     * @param args command line arguments
     */
    /**
     * Main method to benchmark searches.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int datasetSize = 100000;
        int maxId = 200000;
        Product[] products = new Product[datasetSize];

        // Ensure unique IDs from 1 to 200,000
        List<Integer> ids = new ArrayList<>(maxId);
        for (int i = 1; i <= maxId; i++) {
            ids.add(i);
        }
        Collections.shuffle(ids);

        Random random = new Random();
        for (int i = 0; i < datasetSize; i++) {
            products[i] = new Product(
                ids.get(i),
                "Product_" + ids.get(i), // Unique name
                "Category_" + (random.nextInt(10) + 1),
                Math.round(random.nextDouble() * 1000.0 * 100.0) / 100.0,
                random.nextInt(500)
            );
        }

        // Binary search requires sorted array
        long sortStart = System.currentTimeMillis();
        Arrays.sort(products);
        long sortEnd = System.currentTimeMillis();

        // -------------------------
        // Determine Test Cases
        // -------------------------
        // 1. BEST CASE:
        // Sequential Search best case is the very first element (index 0)
        int seqBestId = products[0].getProductId();
        // Binary Search best case is the very middle element
        int binBestId = products[datasetSize / 2].getProductId();

        // 2. WORST CASE:
        // Item is not in the array
        int worstId = -1;

        // 3. AVERAGE CASE:
        // Randomly chosen existing element
        int avgId = products[random.nextInt(datasetSize)].getProductId();

        // -------------------------
        // Run Tests & Measure Times (in nanoseconds)
        // -------------------------
        
        // --- Sequential Search ---
        double seqBestTime = measureSequential(products, seqBestId) / 1000000.0;
        
        // Take an average over 1000 searches to get a stable number for ms representation
        long seqAvgTotal = 0;
        for (int i=0; i<1000; i++) {
            seqAvgTotal += measureSequential(products, products[random.nextInt(datasetSize)].getProductId());
        }
        double seqAvgTime = (seqAvgTotal / 1000.0) / 1000000.0;
        
        double seqWorstTime = measureSequential(products, worstId) / 1000000.0;

        // --- Binary Search ---
        double binBestTime = measureBinary(products, binBestId) / 1000000.0;
        
        long binAvgTotal = 0;
        for (int i=0; i<1000; i++) {
            binAvgTotal += measureBinary(products, products[random.nextInt(datasetSize)].getProductId());
        }
        double binAvgTime = (binAvgTotal / 1000.0) / 1000000.0;
        
        double binWorstTime = measureBinary(products, worstId) / 1000000.0;

        // --- Hybrid Name Search ---
        ProductManager manager = new ProductManager(products);
        long hybridAvgTotal = 0;
        for (int i=0; i<1000; i++) {
            String targetName = products[random.nextInt(datasetSize)].getProductName();
            long start = System.nanoTime();
            manager.searchByName(targetName);
            hybridAvgTotal += (System.nanoTime() - start);
        }
        double hybridAvgTime = (hybridAvgTotal / 1000.0) / 1000000.0;

        // --- Hybrid Insert Operation ---
        long insertTotal = 0;
        int insertCount = 100; // Smaller count because O(N) array copying is slow
        Product[] currentProducts = products;
        for (int i=0; i<insertCount; i++) {
            Product newProduct = new Product(
                maxId + i + 1,
                "NewProduct_" + i,
                "Cat1",
                49.99,
                10
            );
            long start = System.nanoTime();
            currentProducts = manager.addProduct(currentProducts, newProduct);
            insertTotal += (System.nanoTime() - start);
        }
        double insertAvgTime = (insertTotal / (double)insertCount) / 1000000.0;

        // Calculate performance improvement
        double improvement = seqAvgTime / binAvgTime;

        // -------------------------
        // Output Formatted Results
        // -------------------------
        System.out.println("================================================================================");
        System.out.println("TECHMART SEARCH PERFORMANCE ANALYSIS (n = 100,000 products)");
        System.out.println("================================================================================");
        System.out.println();
        System.out.println("SEQUENTIAL SEARCH:");
        System.out.printf("Best Case (ID found at position 0): %.3f ms%n", seqBestTime);
        System.out.printf("Average Case (random ID): %.3f ms%n", seqAvgTime);
        System.out.printf("Worst Case (ID not found): %.3f ms%n", seqWorstTime);
        System.out.println();
        System.out.println("BINARY SEARCH:");
        System.out.printf("Best Case (ID at middle): %.3f ms%n", binBestTime);
        System.out.printf("Average Case (random ID): %.3f ms%n", binAvgTime);
        System.out.printf("Worst Case (ID not found): %.3f ms%n", binWorstTime);
        System.out.println();
        
        System.out.printf("PERFORMANCE IMPROVEMENT: Binary search is ~%.0fx faster on average%n", improvement);
        System.out.println();
        
        System.out.println("HYBRID NAME SEARCH:");
        System.out.printf("Average search time: %.3f ms%n", hybridAvgTime);
        System.out.printf("Average insert time: %.3f ms%n", insertAvgTime);
        System.out.println("================================================================================");
    }

    private static long measureSequential(Product[] products, int targetId) {
        long start = System.nanoTime();
        sequentialSearchById(products, targetId);
        return System.nanoTime() - start;
    }

    private static long measureBinary(Product[] products, int targetId) {
        long start = System.nanoTime();
        binarySearchById(products, targetId);
        return System.nanoTime() - start;
    }
}