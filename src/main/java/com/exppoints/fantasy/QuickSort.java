package com.exppoints.fantasy;

import java.util.ArrayList;
import java.util.Collections;

public class QuickSort {

    public static void quicksort(ArrayList<Player> arr) {
        quicksort(arr, 0, arr.size() - 1);
    }

    private static void quicksort(ArrayList<Player> arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivotIndex = partition(arr, left, right);
        quicksort(arr, left, pivotIndex - 1);
        quicksort(arr, pivotIndex + 1, right);
    }

    private static int partition(ArrayList<Player> arr, int left, int right) {
        Player pivot = arr.get(right);
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (arr.get(j).getScore() > pivot.getScore()) {
                i++;
                Collections.swap(arr, i, j);
            }
        }

        Collections.swap(arr, i + 1, right);
        return i + 1;
    }
}