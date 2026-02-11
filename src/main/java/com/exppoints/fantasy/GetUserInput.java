package com.exppoints.fantasy;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GetUserInput {
    public static List<String> getInput() {
        
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter player names in format first1-last1,first2-last2: ");
        String names = scanner.nextLine(); 
        
        List<String> ret = Arrays.asList(names.split(","));

        return ret;
    }
}