package com.exppoints.fantasy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GetUserInput {
    public static List<String> getInput() {
        List<String> ret = new ArrayList<>();

        // Create a Scanner object to read input from the console (System.in)
        Scanner scanner = new Scanner(System.in);

        // Read a string
        System.out.print("Enter player names in format first1-last1,first2-last2: ");
        String names = scanner.nextLine(); // Reads the whole line
        
        // Convert the resulting array to a List
        ret = Arrays.asList(names.split(","));

        return ret;
    }
}