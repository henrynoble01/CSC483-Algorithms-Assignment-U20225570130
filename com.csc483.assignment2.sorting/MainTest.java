package com.csc483.assignment2.sorting;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

public class MainTest {

    @Test
    public void testInsertionSort() {
        int[] arr = {5, 2, 8, 1, 9};
        Main.Sorter sorter = new Main.InsertionSort();
        sorter.sort(arr);
        Assert.assertArrayEquals(new int[]{1, 2, 5, 8, 9}, arr);
    }

    @Test
    public void testMergeSort() {
        int[] arr = {5, 2, 8, 1, 9, 3};
        Main.Sorter sorter = new Main.MergeSort();
        sorter.sort(arr);
        Assert.assertArrayEquals(new int[]{1, 2, 3, 5, 8, 9}, arr);
    }

    @Test
    public void testQuickSort() {
        int[] arr = {10, -1, 2, 5, 0};
        Main.Sorter sorter = new Main.QuickSort();
        sorter.sort(arr);
        Assert.assertArrayEquals(new int[]{-1, 0, 2, 5, 10}, arr);
    }

    @Test
    public void testEmptyArray() {
        int[] arr = {};
        Main.Sorter[] sorters = {new Main.InsertionSort(), new Main.MergeSort(), new Main.QuickSort()};
        for (Main.Sorter sorter : sorters) {
            int[] testArr = Arrays.copyOf(arr, arr.length);
            sorter.sort(testArr);
            Assert.assertArrayEquals(new int[]{}, testArr);
        }
    }

    @Test
    public void testAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        Main.Sorter[] sorters = {new Main.InsertionSort(), new Main.MergeSort(), new Main.QuickSort()};
        for (Main.Sorter sorter : sorters) {
            int[] testArr = Arrays.copyOf(arr, arr.length);
            sorter.sort(testArr);
            Assert.assertArrayEquals(new int[]{1, 2, 3, 4, 5}, testArr);
        }
    }

    @Test
    public void testDuplicatesArray() {
        int[] arr = {4, 2, 4, 2, 1};
        Main.Sorter[] sorters = {new Main.InsertionSort(), new Main.MergeSort(), new Main.QuickSort()};
        for (Main.Sorter sorter : sorters) {
            int[] testArr = Arrays.copyOf(arr, arr.length);
            sorter.sort(testArr);
            Assert.assertArrayEquals(new int[]{1, 2, 2, 4, 4}, testArr);
        }
    }
}
