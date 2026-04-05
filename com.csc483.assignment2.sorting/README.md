# Assignment 2: Sorting Algorithms

This project contains implementations of basic sorting algorithms (Insertion Sort, Merge Sort, Quick Sort) and benchmarks their performance against datasets of sizes up to N=100,000 using various arrangements (Random, Sorted, Reverse, etc.). 

## Compilation Instructions
```bash
# From the root directory
javac -d . com.csc483.assignment2.sorting/Main.java
```

## Execution Instructions
```bash
# Note: Ensure that you allocate enough heap size if testing larger datasets or increasing scale
# Run from the root directory
java com.csc483.assignment2.sorting.Main
```

## Dependencies
- Java 8 or higher. No external libraries are strictly required for the core benchmarks.

## Sample Usage
```java
int[] array = {5, 2, 8, 1};
Main.Sorter sorter = new Main.MergeSort();
Main.Metrics m = sorter.sort(array);
System.out.println("Time taken: " + m.timeNs + "ns");
```

## Known Limitations
- QuickSort relies on a middle-pivot. While it mitigates worst-case $O(N^2)$ on already sorted arrays, it's not a randomized pivot.
- Memory usage for large arrays can be an issue for MergeSort.
- Saving large datasets locally (e.g. 100,000 rows) can slow down the overall execution loop slightly.

## Testing
Test coverage reports can be generated with a tool like Jacoco or integrated directly via IDEs (like Eclipse or IntelliJ IDEA).
